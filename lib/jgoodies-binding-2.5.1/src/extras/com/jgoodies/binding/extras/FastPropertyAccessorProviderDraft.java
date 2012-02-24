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

package com.jgoodies.binding.extras;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.beans.PropertyAccessor;
import com.jgoodies.binding.beans.PropertyAccessors.PropertyAccessorProvider;
import com.jgoodies.binding.beans.PropertyNotFoundException;
import com.jgoodies.common.bean.AbstractBean;

/**
 * Provides a faster lookup for PropertyAccessors compared to the
 * Introspection-based default PropertyAccessorProvider.
 * This class does not look up BeanInfo classes, and it omits methods
 * declared in the classes: Object, {@link AbstractBean}, and {@link Model}.
 * It does not comply with the Bean specification, but will work in
 * most cases, because typical domain classes do not have BeanInfo classes.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.3 $
 *
 * @since 2.2
 */
public final class FastPropertyAccessorProviderDraft implements
    PropertyAccessorProvider {

    private final Map<Class<?>, CacheEntry> cache =
        new IdentityHashMap<Class<?>, CacheEntry>(100);


    public PropertyAccessor getAccessor(Class<?> beanClass,
        String propertyName, String getterName, String setterName) {
        synchronized (cache) {
            CacheEntry e = cache.get(beanClass);
            if (e == null) {
                e = new CacheEntry(beanClass);
                cache.put(beanClass, e);
            }
            return e.getAccessor(propertyName, getterName, setterName);
        }
    }


    private static final class CacheEntry {

        private final Map<String, Object> methods;
        private final Map<String, PropertyAccessor> accessors;


        CacheEntry(Class<?> beanClass) {
            Method[] publicMethods = beanClass.getMethods();
            int size = publicMethods.length;
            this.methods = new IdentityHashMap<String, Object>(size);
            this.accessors = new IdentityHashMap<String, PropertyAccessor>(size / 2);

            String name;
            for (Method m : publicMethods) {
                Class<?> declaringClass = m.getDeclaringClass();
                if (   Modifier.isStatic(m.getModifiers())
                    || declaringClass == Model.class
                    || declaringClass == AbstractBean.class
                    || declaringClass == Object.class)
                {
                    continue;
                }
                Class<?>[] ptypes = m.getParameterTypes();
                Class<?> rtype = m.getReturnType();
                if (   ptypes.length > 0
                    && rtype != void.class
                    && rtype != Void.class)
                {
                    // Ignore methods which may not be used for properties.
                    continue;
                }
                name = m.getName();
                if (name.startsWith("set")) {
                    if (ptypes.length == 0 || ptypes.length > 1) {
                        continue;
                    }
                    Object o = methods.get(name);
                    if (o == null) {
                        methods.put(name, m);
                    } else if (o instanceof Method) {
                        Map<Class<?>, Method> map =
                            new IdentityHashMap<Class<?>, Method>(10);
                        map.put(ptypes[0], m);
                        Method other = (Method) o;
                        Class<?>[] otherTypes = other.getParameterTypes();
                        map.put(otherTypes[0], other);
                        methods.put(name, map);
                    } else if (o instanceof Map<?, ?>) {
                        @SuppressWarnings("unchecked")
                        Map<Class<?>, Method> map = (Map<Class<?>, Method>) o;
                        map.put(ptypes[0], m);
                    }
                } else {
                    methods.put(name, m);
                }
            }
        }


        PropertyAccessor getAccessor(String propertyName, String getterName,
            String setterName) {
            PropertyAccessor d = accessors.get(propertyName);
            if (d != null) {
                return d;
            }
            if (setterName != null || getterName != null) {
                Method readMethod = (Method) methods.get(getterName);
                Method writeMethod = (Method) methods.get(setterName);
                PropertyAccessor pa = new PropertyAccessor(propertyName, readMethod, writeMethod);
                accessors.put(propertyName, pa);
                return pa;
            }
            String methodSuffix = Character.toUpperCase(propertyName.charAt(0))
                + propertyName.substring(1);

            // Reader
            String getter0 = ("get" + methodSuffix).intern();
            Method readMethod = (Method) methods.get(getter0);
            if (readMethod == null) {
                String getter1 = ("is" + methodSuffix).intern();
                readMethod = (Method) methods.get(getter1);
            }
            Class<?> rtype = null;
            if (readMethod != null) {
                rtype = readMethod.getReturnType();
            }

            // Writer
            String setter  = ("set" + methodSuffix).intern();
            Object o = methods.get(setter);
            Method writeMethod = null;
            if (o instanceof Method) {
                writeMethod = (Method) o;
            } else if (o instanceof Map<?, ?>) {
                @SuppressWarnings("unchecked")
                Map<Class<?>, Method> map = (Map<Class<?>, Method>) o;
                writeMethod = map.get(rtype);
            }

            if (readMethod != null && writeMethod != null) {
                Class<?>[] ptypes = writeMethod.getParameterTypes();
                if (   ptypes.length > 0
                    && ptypes[0] != rtype
                    && rtype != null
                    && !rtype.isAssignableFrom(ptypes[0]))
                {
                    throw new IllegalStateException(
                          "Type mismatch between read and write methods."
                        + "\npropertyName=" + propertyName
                        + "\nread method =" + readMethod
                        + "\nwrite method=" + writeMethod);
                }
            }

            if (readMethod == null && writeMethod == null) {
                throw new PropertyNotFoundException(propertyName, null);
            }
            d = new PropertyAccessor(propertyName, readMethod, writeMethod);
            accessors.put(propertyName, d);
            return d;
        }

    }

}
