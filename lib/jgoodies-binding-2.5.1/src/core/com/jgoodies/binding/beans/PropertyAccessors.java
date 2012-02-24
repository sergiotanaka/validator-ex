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

import static com.jgoodies.common.base.Preconditions.checkNotNull;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;


/**
 * Manages the PropertyAccessProvider.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.5 $
 *
 * @since 2.2
 */
public final class PropertyAccessors {

    private static PropertyAccessorProvider instance =
        new IntrospectionPropertyAccessorProvider();
        //new FastPropertyAccessorProvider();


    private PropertyAccessors() {
        // Overrides default constructor; prevents instantiation.
    }


    /**
     * @return the current PropertyAccessorProvider
     */
    public static PropertyAccessorProvider getProvider() {
        return instance;
    }


    /**
     * Sets a new PropertyAccessorProvider.
     *
     * @param provider   the new provider to be used
     *
     * @throws NullPointerException if {@code provider} is {@code null}
     */
    public static void setProvider(PropertyAccessorProvider provider) {
        checkNotNull(provider, "The provider must not be null.");
        instance = provider;
    }


    // Nested Types ***********************************************************

    /**
     * Just describes how to look up a PropertyAccessor.
     */
    public interface PropertyAccessorProvider {

        /**
         * Looks up and returns a PropertyAccessor for the given bean class
         * and the specified property, getter, and setter.
         *
         * @param beanClass   the class the provides the property
         * @param propertyName
         *     the name of the property, e.g. "name", "enabled"
         * @param getterName
         *     the name of the getter, e.g. "getName", "isEnabled"
         * @param setterName
         *     the name of the setter, e.g. "setName", "setEnabled"
         *
         * @return a PropertyAccessor for the given property that
         *    reads property values using a read method with the given
         *    getter name, and sets property values using a write method
         *    with the given setter name
         */
        PropertyAccessor getAccessor(Class<?> beanClass,
            String propertyName, String getterName, String setterName);

    }


    /**
     * Uses the standard Bean Introspection to look up PropertyDescriptors
     * that in turn are used to build and return PropertyAccessors.
     */
    public static class IntrospectionPropertyAccessorProvider implements PropertyAccessorProvider {

        public PropertyAccessor getAccessor(Class<?> beanClass,
            String propertyName, String getterName, String setterName) {
            PropertyDescriptor pd = getPropertyDescriptor(beanClass,
                propertyName, getterName, setterName);
            return new PropertyAccessor(propertyName, pd.getReadMethod(), pd.getWriteMethod());
        }


        /**
         * Looks up and returns a {@code PropertyDescriptor} for the
         * given Java Bean class and property name using the standard
         * Java Bean introspection behavior.
         *
         * @param beanClass     the type of the bean that holds the property
         * @param propertyName  the name of the Bean property
         * @return the {@code PropertyDescriptor} associated with the given
         *     bean and property name as returned by the Bean introspection
         *
         * @throws IntrospectionException if an exception occurs during
         *     introspection.
         * @throws NullPointerException if the beanClass or propertyName is {@code null}
         *
         * @since 1.1.1
         */
        private static PropertyDescriptor getPropertyDescriptor(
            Class<?> beanClass,
            String propertyName)
            throws IntrospectionException {

            BeanInfo info = Introspector.getBeanInfo(beanClass);
            for (PropertyDescriptor element : info.getPropertyDescriptors()) {
                if (propertyName.equals(element.getName())) {
                    return element;
                }
            }
            throw new IntrospectionException(
                "Property '" + propertyName + "' not found in bean " + beanClass);
        }


        /**
         * Looks up and returns a {@code PropertyDescriptor} for the given
         * Java Bean class and property name. If a getter name or setter name
         * is available, these are used to create a PropertyDescriptor.
         * Otherwise, the standard Java Bean introspection is used to determine
         * the property descriptor.
         *
         * @param beanClass     the class of the bean that holds the property
         * @param propertyName  the name of the property to be accessed
         * @param getterName    the optional name of the property's getter
         * @param setterName    the optional name of the property's setter
         * @return the {@code PropertyDescriptor} associated with the
         *     given bean and property name
         *
         * @throws PropertyNotFoundException   if the property could not be found
         *
         * @since 1.1.1
         */
        private static PropertyDescriptor getPropertyDescriptor(
           Class<?> beanClass, String propertyName, String getterName, String setterName) {
            try {
                return
                    getterName != null || setterName != null
                        ? new PropertyDescriptor(
                            propertyName,
                            beanClass,
                            getterName,
                            setterName)
                        : getPropertyDescriptor(
                            beanClass,
                            propertyName);
            } catch (IntrospectionException e) {
                throw new PropertyNotFoundException(propertyName, beanClass, e);
            }
        }


    }


}
