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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import com.jgoodies.common.bean.AbstractBean;

/**
 * An abstract superclass that minimizes the effort required to provide
 * change support for bound and constrained Bean properties.
 * This class follows the conventions and recommendations as described
 * in the <a href="http://java.sun.com/products/javabeans/docs/spec.html"
 * >Java Bean Specification</a>.<p>
 *
 * Uses class {@link com.jgoodies.binding.beans.ExtendedPropertyChangeSupport},
 * to enable the {@code ==} or {@code #equals} test when
 * changing values.<p>
 *
 * TODO: Consider adding a method {@code #fireChange} that invokes
 * {@code #firePropertyChange} if and only if
 * {@code new value != old value}. The background is, that
 * {@code #firePropertyChange} must fire an event
 * if {@code new value==null==old value}.<p>
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.22 $
 *
 * @see     Model
 * @see     java.beans.PropertyChangeEvent
 * @see     PropertyChangeListener
 * @see     PropertyChangeSupport
 * @see     ExtendedPropertyChangeSupport
 * @see     VetoableChangeListener
 * @see     VetoableChangeSupport
 */
public abstract class Model extends AbstractBean {


     // Overriding Superclass Behavior ****************************************

     /**
      * Creates and returns a PropertyChangeSupport for the given bean.
      * Invoked by the first call to {@link #addPropertyChangeListener}
      * when lazily creating the sole change support instance used throughout
      * this bean.<p>
      *
      * This implementation creates an extended change support that allows
      * to configure whether the old and new value are compared with
      * {@code ==} or {@code equals}.
      *
      * @param bean      the bean to create a change support for
      * @return the new change support
      */
     @Override
     protected PropertyChangeSupport createPropertyChangeSupport(Object bean) {
         return new ExtendedPropertyChangeSupport(bean);
     }


     // Managing Property Change Listeners **********************************

    /**
     * Support for reporting bound property changes for Object properties.
     * This method can be called when a bound property has changed and it will
     * send the appropriate PropertyChangeEvent to any registered
     * PropertyChangeListeners.<p>
     *
     * The boolean parameter specifies whether the old and new value are
     * compared with {@code ==} or {@code equals}.
     *
     * @param propertyName      the property whose value has changed
     * @param oldValue          the property's previous value
     * @param newValue          the property's new value
     * @param checkIdentity     true to check differences using {@code ==}
     *     false to use {@code equals}.
     */
    protected final void firePropertyChange(
            String propertyName,
            Object oldValue,
            Object newValue,
            boolean checkIdentity) {
        if (changeSupport == null) {
            return;
        }
        ((ExtendedPropertyChangeSupport) changeSupport).firePropertyChange(
            propertyName,
            oldValue,
            newValue,
            checkIdentity);
    }


}
