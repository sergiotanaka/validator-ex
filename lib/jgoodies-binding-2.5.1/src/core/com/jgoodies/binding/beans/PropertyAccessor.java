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

import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.jgoodies.common.base.Preconditions;

/**
 * An unmodifiable Object that describes and provides access to a bean property.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.2 $
 *
 * @since 2.2
 */
public final class PropertyAccessor {

    private final String propertyName;
    private final Method readMethod;
    private final Method writeMethod;


    // Instance Creation ------------------------------------------------------

    /**
     * Constructs a PropertyAcessor for the given property name,
     * reader and writer.
     *
     * @param propertyName
     *     the name of the property, e.g. "name", "enabled"
     * @param readMethod
     *     the method that returns the property value,
     *     e.g. {@code getName()}, {@code isEnabled()}
     * @param writeMethod
     *     the method that sets the property value,
     *     e.g. {@code setName(String)}, {@code setEnabled(boolean)}
     * @throws NullPointerException if {@code propertyName} is null
     */
    public PropertyAccessor(String propertyName,
            Method readMethod,
            Method writeMethod) {
        checkNotNull(propertyName, "The property name must not be null.");
        Preconditions.checkArgument(readMethod != null || writeMethod != null,
                "Either the reader or writer must not be null.");
        this.propertyName = propertyName;
        this.readMethod   = readMethod;
        this.writeMethod  = writeMethod;
    }


    // Accessors -------------------------------------------------------------

    /**
     * @return the bean property name.
     */
    public String getPropertyName() {
        return propertyName;
    }


    /**
     * @return the type of the accessed bean property
     */
    public Class<?> getPropertyType() {
        return readMethod != null
            ? readMethod.getReturnType()
            : writeMethod.getParameterTypes()[0];
    }


    /**
     * @return the Method used to read the property value
     *    or {@code null}, if not available
     */
    public Method getReadMethod() {
        return readMethod;
    }


    /**
     * @return the Method used to write the property value
     *     or {@code null}, if not available
     */
    public Method getWriteMethod() {
        return writeMethod;
    }


    /**
     * @return {@code true} if the property cannot be written,
     *     {@code false} if it can be written
     */
    public boolean isReadOnly() {
        return writeMethod == null;
    }


    /**
     * @return {@code true} if the property cannot be read,
     *     {@code false} if it can be read
     */
    public boolean isWriteOnly() {
        return readMethod == null;
    }


    // Operations -------------------------------------------------------------

    /**
     * Invokes this accessor's reader on the given bean.
     *
     * @return the value of the property that is described by this accessor
     *    when read from the given bean
     *
     * @param bean   the target bean where the reader is to be invoked
     *
     * @throws NullPointerException if {@code bean} is {@code null}
     * @throws UnsupportedOperationException if the property is write-only
     */
    public Object getValue(Object bean) {
        checkNotNull(bean, "The bean must not be null.");
        if (isWriteOnly()) {
            throw new UnsupportedOperationException(
                "The property '" + propertyName + "' is write-only.");
        }
        try {
            return readMethod.invoke(bean, (Object[]) null);
        } catch (InvocationTargetException e) {
            throw PropertyAccessException.createReadAccessException(
                bean, this, e.getCause());
        } catch (IllegalAccessException e) {
            throw PropertyAccessException.createReadAccessException(
                bean, this, e);
        }
    }


    /**
     * Invokes this accessor's writer on the given bean with the given value.
     *
     * @param bean     the target bean where the property value shall be set
     * @param newValue the value to set
     *
     * @throws NullPointerException if {@code bean} is {@code null}
     * @throws UnsupportedOperationException if the property is write-only
     * @throws PropertyVetoException if the invoked writer throws such an exception
     */
    public void setValue(Object bean, Object newValue)
        throws PropertyVetoException {
        checkNotNull(bean, "The bean must not be null.");
        if (isReadOnly()) {
            throw new UnsupportedOperationException(
                "The property '" + propertyName + "' is read-only.");
        }
        try {
            writeMethod.invoke(bean, newValue);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof PropertyVetoException) {
                throw (PropertyVetoException) cause;
            }
            throw PropertyAccessException.createWriteAccessException(
                    bean, newValue, this, cause);
        } catch (IllegalAccessException e) {
            throw PropertyAccessException.createWriteAccessException(
                bean, newValue, this, e);
        } catch (IllegalArgumentException e) {
            throw PropertyAccessException.createWriteAccessException(
                bean, newValue, this, e);
        }
    }


    // Overriding Superclass Behavior -----------------------------------------

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        PropertyAccessor other = (PropertyAccessor) obj;
        if (propertyName == null) {
            if (other.propertyName != null) { return false; }
        } else if (!propertyName.equals(other.propertyName)) { return false; }
        if (readMethod == null) {
            if (other.readMethod != null) { return false; }
        } else if (!readMethod.equals(other.readMethod)) { return false; }
        if (writeMethod == null) {
            if (other.writeMethod != null) { return false; }
        } else if (!writeMethod.equals(other.writeMethod)) { return false; }
        return true;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +
                (propertyName == null ? 0 : propertyName.hashCode());
        result = prime * result +
                (readMethod == null ? 0 : readMethod.hashCode());
        result = prime * result +
                (writeMethod == null ? 0 : writeMethod.hashCode());
        return result;
    }

}
