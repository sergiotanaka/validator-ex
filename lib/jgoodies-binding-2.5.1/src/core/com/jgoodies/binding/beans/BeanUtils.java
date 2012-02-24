/*
 * Copyright (c) 2002-2011 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jgoodies.binding.beans;

import static com.jgoodies.common.base.Preconditions.checkArgument;
import static com.jgoodies.common.base.Preconditions.checkNotNull;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.jgoodies.common.bean.ObservableBean;
import com.jgoodies.common.bean.ObservableBean2;

/**
 * Consists exclusively of static methods that provide
 * convenience behavior for working with Java Bean properties.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.22 $
 *
 * @see     Introspector
 * @see     BeanInfo
 * @see     PropertyDescriptor
 */
public final class BeanUtils {


    private BeanUtils() {
        // Override default constructor; prevents instantiation.
    }


    /**
     * Checks and answers whether the given class supports bound properties,
     * i.e. it provides a pair of multicast event listener registration methods
     * for {@code PropertyChangeListener}s:
     * <pre>
     * public void addPropertyChangeListener(PropertyChangeListener x);
     * public void removePropertyChangeListener(PropertyChangeListener x);
     * </pre>
     *
     * @param clazz    the class to test
     * @return true if the class supports bound properties, false otherwise
     */
    public static boolean supportsBoundProperties(Class<?> clazz) {
        return  getPCLAdder(clazz)   != null
             && getPCLRemover(clazz) != null;
    }


    /**
     * Holds the class parameter list that is used to lookup
     * the adder and remover methods for PropertyChangeListeners.
     */
    private static final Class<?>[] PCL_PARAMS =
        new Class<?>[] {PropertyChangeListener.class};


    /**
     * Holds the class parameter list that is used to lookup
     * the adder and remover methods for PropertyChangeListeners.
     */
    private static final Class<?>[] NAMED_PCL_PARAMS =
        new Class<?>[] {String.class, PropertyChangeListener.class};


    /**
     * Looks up and returns the method that adds a multicast
     * PropertyChangeListener to instances of the given class.
     *
     * @param clazz   the class that provides the adder method
     * @return the method that adds multicast PropertyChangeListeners
     */
    public static Method getPCLAdder(Class<?> clazz) {
        try {
            return clazz.getMethod("addPropertyChangeListener", PCL_PARAMS);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }


    /**
     * Looks up and returns the method that removes a multicast
     * PropertyChangeListener from instances of the given class.
     *
     * @param clazz   the class that provides the remover method
     * @return the method that removes multicast PropertyChangeListeners
     */
    public static Method getPCLRemover(Class<?> clazz) {
        try {
            return clazz.getMethod("removePropertyChangeListener", PCL_PARAMS);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }


    /**
     * Looks up and returns the method that adds a PropertyChangeListener
     * for a specified property name to instances of the given class.
     *
     * @param clazz   the class that provides the adder method
     * @return the method that adds the PropertyChangeListeners
     */
    public static Method getNamedPCLAdder(Class<?> clazz) {
        try {
            return clazz.getMethod("addPropertyChangeListener", NAMED_PCL_PARAMS);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }


    /**
     * Looks up and returns the method that removes a PropertyChangeListener
     * for a specified property name from instances of the given class.
     *
     * @param clazz   the class that provides the remover method
     * @return the method that removes the PropertyChangeListeners
     */
    public static Method getNamedPCLRemover(Class<?> clazz) {
        try {
            return clazz.getMethod("removePropertyChangeListener", NAMED_PCL_PARAMS);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }


    /**
     * Adds a property change listener to the given bean. First checks
     * whether the bean supports <em>bound properties</em>, i.e. it provides
     * a pair of methods to register multicast property change event listeners;
     * see section 7.4.1 of the Java Beans specification for details.<p>
     *
     * {@code beanClass} can be a type other than {@code bean.getClass()},
     * for example if the bean is specified by public interfaces,
     * and implemented by package private class.
     *
     * @param bean          the bean to add the property change listener to
     * @param beanClass     the Bean class used to lookup methods from
     * @param listener      the listener to add
     *
     * @throws NullPointerException
     *     if the bean or listener is {@code null}
     * @throws IllegalArgumentException
     *     if the bean is not an instance of the bean class
     * @throws PropertyUnboundException
     *     if the bean does not support bound properties
     * @throws PropertyNotBindableException
     *     if the property change handler cannot be added successfully
     *
     * @since 1.1.1
     */
    public static void addPropertyChangeListener(Object bean, Class<?> beanClass, PropertyChangeListener listener) {
        checkNotNull(listener, "The listener must not be null.");
        if (beanClass == null) {
            beanClass = bean.getClass();
        } else {
            checkArgument(beanClass.isInstance(bean),
                    "The bean %s must be an instance of %s", bean, beanClass);
        }

        if (bean instanceof ObservableBean) {
            ((ObservableBean) bean).addPropertyChangeListener(listener);
            return;
        }

        // Check whether the bean supports bound properties.
        if (!BeanUtils.supportsBoundProperties(beanClass)) {
            throw new PropertyUnboundException(
                "Bound properties unsupported by bean class=" + beanClass
              + "\nThe Bean class must provide a pair of methods:"
              + "\npublic void addPropertyChangeListener(PropertyChangeListener x);"
              + "\npublic void removePropertyChangeListener(PropertyChangeListener x);");
        }

        Method multicastPCLAdder = getPCLAdder(beanClass);
        try {
            multicastPCLAdder.invoke(bean, listener);
        } catch (InvocationTargetException e) {
            throw new PropertyNotBindableException(
                "Due to an InvocationTargetException we failed to add "
              + "a multicast PropertyChangeListener to bean: " + bean, e.getCause());
        } catch (IllegalAccessException e) {
            throw new PropertyNotBindableException(
                "Due to an IllegalAccessException we failed to add "
              + "a multicast PropertyChangeListener to bean: " + bean, e);
        }
    }


    /**
     * Adds a named property change listener to the given bean. The bean
     * must provide the optional support for listening on named properties
     * as described in section 7.4.5 of the
     * <a href="http://java.sun.com/products/javabeans/docs/spec.html">Java Bean
     * Specification</a>. The bean class must provide the method:
     * <pre>
     * public void addPropertyChangeListener(String name, PropertyChangeListener l);
     * </pre><p>
     *
     * {@code beanClass} can be a type other than {@code bean.getClass()},
     * for example if the bean is specified by public interfaces,
     * and implemented by package private class.
     *
     * @param bean          the bean to add a property change handler
     * @param beanClass     the Bean class used to lookup methods from
     * @param propertyName  the name of the property to be observed
     * @param listener      the listener to add
     *
     * @throws NullPointerException
     *     if the bean, propertyName or listener is {@code null}
     * @throws IllegalArgumentException
     *     if the bean is not an instance of the bean class
     * @throws PropertyNotBindableException
     *     if the property change handler cannot be added successfully
     */
    public static void addPropertyChangeListener(
             Object bean,
             Class<?> beanClass,
             String propertyName,
             PropertyChangeListener listener) {
        checkNotNull(propertyName, "The property name must not be null.");
        checkNotNull(listener, "The listener must not be null.");
        if (beanClass == null) {
            beanClass = bean.getClass();
        } else {
            checkArgument(beanClass.isInstance(bean),
                    "The bean %s must be an instance of %s", bean, beanClass);
        }
        if (bean instanceof ObservableBean2) {
            ((ObservableBean2) bean).addPropertyChangeListener(propertyName, listener);
            return;
        }
        Method namedPCLAdder = getNamedPCLAdder(beanClass);
        if (namedPCLAdder == null) {
            throw new PropertyNotBindableException(
                "Could not find the bean method"
              + "\npublic void addPropertyChangeListener(String, PropertyChangeListener);"
              + "\nin bean: " + bean);
        }
        try {
            namedPCLAdder.invoke(bean, propertyName, listener);
        } catch (InvocationTargetException e) {
            throw new PropertyNotBindableException(
                "Due to an InvocationTargetException we failed to add "
              + "a named PropertyChangeListener to bean: " + bean, e.getCause());
        } catch (IllegalAccessException e) {
            throw new PropertyNotBindableException(
                "Due to an IllegalAccessException we failed to add "
              + "a named PropertyChangeListener to bean: " + bean, e);
        }
    }


    /**
     * Adds a property change listener to the given bean. First checks
     * whether the bean supports <em>bound properties</em>, i.e. it provides
     * a pair of methods to register multicast property change event listeners;
     * see section 7.4.1 of the Java Beans specification for details.
     *
     * @param bean          the bean to add the property change listener to
     * @param listener      the listener to add
     *
     * @throws NullPointerException
     *     if the bean or listener is {@code null}
     * @throws PropertyUnboundException
     *     if the bean does not support bound properties
     * @throws PropertyNotBindableException
     *     if the property change handler cannot be added successfully
     */
    public static void addPropertyChangeListener(Object bean, PropertyChangeListener listener) {
        addPropertyChangeListener(bean, (Class<?>) null, listener);
    }


    /**
     * Adds a named property change listener to the given bean. The bean
     * must provide the optional support for listening on named properties
     * as described in section 7.4.5 of the
     * <a href="http://java.sun.com/products/javabeans/docs/spec.html">Java Bean
     * Specification</a>. The bean class must provide the method:
     * <pre>
     * public void addPropertyChangeListener(String name, PropertyChangeListener l);
     * </pre>
     *
     * @param bean          the bean to add a property change handler
     * @param propertyName  the name of the property to be observed
     * @param listener      the listener to add
     *
     * @throws NullPointerException
     *     if the bean, propertyName or listener is {@code null}
     * @throws PropertyNotBindableException
     *     if the property change handler cannot be added successfully
     */
    public static void addPropertyChangeListener(
             Object bean,
             String propertyName,
             PropertyChangeListener listener) {
        addPropertyChangeListener(bean, null, propertyName, listener);
    }


    /**
     * Removes a property change listener from the given bean.<p>
     *
     * {@code beanClass} can be a type other than {@code bean.getClass()},
     * for example if the bean is specified by public interfaces,
     * and implemented by package private class.
     *
     * @param bean          the bean to remove the property change listener from
     * @param beanClass     the Java Bean class used to lookup methods from
     * @param listener      the listener to remove
     *
     * @throws NullPointerException
     *     if the bean or listener is {@code null}
     * @throws IllegalArgumentException
     *     if the bean is not an instance of the bean class
     * @throws PropertyUnboundException
     *     if the bean does not support bound properties
     * @throws PropertyNotBindableException
     *     if the property change handler cannot be removed successfully
     *
     * @since 1.1.1
     */
    public static void removePropertyChangeListener(Object bean, Class<?> beanClass, PropertyChangeListener listener) {
        checkNotNull(listener, "The listener must not be null.");
        if (beanClass == null) {
            beanClass = bean.getClass();
        } else {
            checkArgument(beanClass.isInstance(bean),
                    "The bean %s must be an instance of %s", bean, beanClass);
        }
        if (bean instanceof ObservableBean) {
            ((ObservableBean) bean).removePropertyChangeListener(listener);
            return;
        }
        Method multicastPCLRemover = getPCLRemover(beanClass);
        if (multicastPCLRemover == null) {
            throw new PropertyUnboundException(
                "Could not find the method:"
              + "\npublic void removePropertyChangeListener(String, PropertyChangeListener x);"
              + "\nfor bean:" + bean);
        }
        try {
            multicastPCLRemover.invoke(bean, listener);
        } catch (InvocationTargetException e) {
            throw new PropertyNotBindableException(
                "Due to an InvocationTargetException we failed to remove "
              + "a multicast PropertyChangeListener from bean: " + bean, e.getCause());
        } catch (IllegalAccessException e) {
            throw new PropertyNotBindableException(
                "Due to an IllegalAccessException we failed to remove "
              + "a multicast PropertyChangeListener from bean: " + bean, e);
        }
    }


    /**
     * Removes a named property change listener from the given bean. The bean
     * must provide the optional support for listening on named properties
     * as described in section 7.4.5 of the
     * <a href="http://java.sun.com/products/javabeans/docs/spec.html">Java Bean
     * Specification</a>. The bean class must provide the method:
     * <pre>
     * public void removePropertyChangeHandler(String name, PropertyChangeListener l);
     * </pre><p>
     *
     * {@code beanClass} can be a type other than {@code bean.getClass()},
     * for example if the bean is specified by public interfaces,
     * and implemented by package private class.
     *
     * @param bean          the bean to remove the property change listener from
     * @param beanClass     the Java Bean class used to lookup methods from
     * @param propertyName  the name of the observed property
     * @param listener      the listener to remove
     *
     * @throws NullPointerException
     *     if the bean, propertyName, or listener is {@code null}
     * @throws IllegalArgumentException
     *     if the bean is not an instance of the bean class
     * @throws PropertyNotBindableException
     *     if the property change handler cannot be removed successfully
     *
     * @since 1.1.1
     */
    public static void removePropertyChangeListener(
             Object bean,
             Class<?> beanClass,
             String propertyName,
             PropertyChangeListener listener) {
        checkNotNull(propertyName, "The property name must not be null.");
        checkNotNull(listener, "The listener must not be null.");
        if (beanClass == null) {
            beanClass = bean.getClass();
        } else {
            checkArgument(beanClass.isInstance(bean),
                    "The bean %s must be an instance of %s", bean, beanClass);
        }
        if (bean instanceof ObservableBean2) {
            ((ObservableBean2) bean).removePropertyChangeListener(propertyName, listener);
            return;
        }
        Method namedPCLRemover = getNamedPCLRemover(beanClass);
        if (namedPCLRemover == null) {
            throw new PropertyNotBindableException(
                "Could not find the bean method"
              + "\npublic void removePropertyChangeListener(String, PropertyChangeListener);"
              + "\nin bean: " + bean);
        }
        try {
            namedPCLRemover.invoke(bean, propertyName, listener);
        } catch (InvocationTargetException e) {
            throw new PropertyNotBindableException(
                "Due to an InvocationTargetException we failed to remove "
              + "a named PropertyChangeListener from bean: " + bean, e.getCause());
        } catch (IllegalAccessException e) {
            throw new PropertyNotBindableException(
                "Due to an IllegalAccessException we failed to remove "
              + "a named PropertyChangeListener from bean: " + bean, e);
        }
    }


    /**
     * Removes a property change listener from the given bean.
     *
     * @param bean          the bean to remove the property change listener from
     * @param listener      the listener to remove
     * @throws NullPointerException if the bean or listener is {@code null}
     * @throws PropertyUnboundException
     *     if the bean does not support bound properties
     * @throws PropertyNotBindableException
     *     if the property change handler cannot be removed successfully
     */
    public static void removePropertyChangeListener(Object bean, PropertyChangeListener listener) {
        removePropertyChangeListener(bean, (Class<?>)null, listener);
    }


    /**
     * Removes a named property change listener from the given bean. The bean
     * must provide the optional support for listening on named properties
     * as described in section 7.4.5 of the
     * <a href="http://java.sun.com/products/javabeans/docs/spec.html">Java Bean
     * Specification</a>. The bean class must provide the method:
     * <pre>
     * public void removePropertyChangeHandler(String name, PropertyChangeListener l);
     * </pre><p>
     *
     * {@code beanClass} can be a type other than {@code bean.getClass()},
     * for example if the bean is specified by public interfaces,
     * and implemented by package private class.
     *
     * @param bean          the bean to remove the property change listener from
     * @param propertyName  the name of the observed property
     * @param listener      the listener to remove
     * @throws NullPointerException
     *     if the bean, propertyName, or listener is {@code null}
     * @throws PropertyNotBindableException
     *     if the property change handler cannot be removed successfully
     */
    public static void removePropertyChangeListener(
             Object bean,
             String propertyName,
             PropertyChangeListener listener) {
        removePropertyChangeListener(bean, null, propertyName, listener);
    }


}
