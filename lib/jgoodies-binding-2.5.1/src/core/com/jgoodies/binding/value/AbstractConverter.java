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

package com.jgoodies.binding.value;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * An abstract class that minimizes the effort required to implement
 * a type converter. A type converter is a ValueModel that converts the type
 * of an object being held as a value in one ValueModel into another type.<p>
 *
 * More formally, a converting ValueModel <i>VM1</i> converts the type
 * <i>T2</i> of an object being held as a value in one ValueModel <i>VM2</i>
 * into another type <i>T1</i>. When reading a value from VM1,
 * instances of T2 are read from VM2 and are converted to T1. When storing
 * a new value to VM1, the type converter will perform the inverse conversion
 * and will convert an instance of T1 to T2.<p>
 *
 * The conversion must be performed when reading and writing values,
 * as well as in the change notification. This class specifies abstract
 * methods for the conversion from source to output, which is used to
 * convert in {@code #getValue} and in the change notification.
 * For the write conversion you must implement {@code #setValue}.<p>
 *
 * Most converters can set values converted by {@code #convertFromSubject}
 * with {@code #setValue}. However, a converter may reject subject values
 * to be converted and may reject values to be set - as any ValueModel.<p>
 *
 * Type converters should be used judiciously and only to bridge two
 * {@code ValueModel}s. Converters often use a generic but weak
 * conversion, and so can be limited w.r.t. to localized
 * formatting conventions.<p>
 *
 * When binding non-String values to a text UI component, consider
 * using a {@link javax.swing.JFormattedTextField}. Formatted text fields
 * provide a powerful means to convert strings to objects and handle many cases
 * that arise around invalid input. Formatted text fields can be bound
 * to {@code ValueModel}s using the
 * {@link com.jgoodies.binding.beans.PropertyConnector} class.
 *
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.15 $
 *
 * @see     ValueModel
 * @see     ConverterFactory
 * @see     javax.swing.JFormattedTextField
 * @see     com.jgoodies.binding.beans.PropertyConnector
 */
public abstract class AbstractConverter extends AbstractWrappedValueModel {

    /**
     * Holds the ValueModel that in turn holds the source value.
     */
    protected final ValueModel subject;


    // Instance creation ******************************************************

    /**
     * Constructs an AbstractConverter on the given subject.
     *
     * @param subject  the ValueModel that holds the source value
     * @throws NullPointerException if the subject is {@code null}
     */
    public AbstractConverter(ValueModel subject) {
        super(subject);
        this.subject = subject;
    }


    // Abstract Behavior ******************************************************

    /**
     * Converts a value from the subject to the type or format used
     * by this converter.
     *
     * @param subjectValue  the subject's value
     * @return the converted value in the type or format used by this converter
     */
    public abstract Object convertFromSubject(Object subjectValue);


    // ValueModel Implementation **********************************************

    /**
     * Converts the subject's value and returns the converted value.
     *
     * @return the converted subject value
     */
    public Object getValue() {
        return convertFromSubject(subject.getValue());
    }


    // AbstractWrappedValueModel Behavior *************************************

    @Override
    protected PropertyChangeListener createValueChangeHandler() {
        return new ValueChangeHandler();
    }


    // Helper Class ***********************************************************

    /**
     * Listens to value changes in the wrapped model, converts the old and
     * new value - if any - and fires a value change for this converter.
     */
    private final class ValueChangeHandler implements PropertyChangeListener {

        /**
         * Notifies listeners about a change in the underlying subject.
         * The old and new value used in the PropertyChangeEvent to be fired
         * are converted versions of the observed old and new values.
         * The observed old and new value are converted only
         * if they are non-null. This is because {@code null}
         * may be a valid value or may indicate <em>not available</em>.<p>
         *
         * TODO: We may need to check the identity, not equity.
         *
         * @param evt   the property change event to be handled
         */
        public void propertyChange(PropertyChangeEvent evt) {
            Object convertedOldValue = evt.getOldValue() == null
                ? null
                : convertFromSubject(evt.getOldValue());
            Object convertedNewValue = evt.getNewValue() == null
                ? null
                : convertFromSubject(evt.getNewValue());
            fireValueChange(convertedOldValue, convertedNewValue);
        }


    }


}
