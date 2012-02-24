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

package com.jgoodies.binding.binder;

import static com.jgoodies.common.base.Preconditions.checkArgument;
import static com.jgoodies.common.base.Preconditions.checkNotBlank;
import static com.jgoodies.common.base.Preconditions.checkNotNull;

import java.text.Format;
import java.util.List;

import javax.swing.*;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ConverterFactory;
import com.jgoodies.binding.value.ValueModel;



/**
 * A binding builder that holds a ValueModel that can be bound
 * to a variety of components. Also provides methods that operate
 * on the ValueModel (conversion) or create a SelectionInListBuilder
 * that in turn can be bound to combo boxes, lists, and tables.<p>
 *
 * It is recommended to create this builder with an optional property name.
 * If such a name is available, it is set a default validation message key
 * for components that are bound with this builder.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.14 $
 *
 * @since 2.3
 */
public class ValueModelBindingBuilderImpl implements ValueModelBindingBuilder {


    private final ValueModel valueModel;
    private final String propertyName;


    // Instance Creation ******************************************************

    /**
     * Creates a ValueModelBindingBuilderImpl for the given ValueModel.
     * It is recommended to favor the constructor
     * {@link ValueModelBindingBuilderImpl#ValueModelBindingBuilderImpl(ValueModel, String)}
     * where the property name is set.
     *
     * @param valueModel    the ValueModel to be managed and bound
     *
     * @throws NullPointerException if {@code valueModel} is {@code null}
     */
    public ValueModelBindingBuilderImpl(ValueModel valueModel) {
        this(valueModel, null);
    }


    /**
     * Creates a ValueModelBindingBuilderImpl for the given ValueModel
     * using the given bean property name.
     *
     * @param valueModel    the ValueModel to be managed and bound
     * @param propertyName  the bean property name that has been used
     *     to create the ValueModel from
     *
     * @throws NullPointerException
     *     if {@code valueModel} is {@code null}
     * @throws IllegalArgumentException
     *     if {@code propertyName} is empty or whitespace
     */
    public ValueModelBindingBuilderImpl(ValueModel valueModel, String propertyName) {
        this.valueModel   = checkNotNull(valueModel, "The ValueModel must not be null.");
        this.propertyName = propertyName;
        if (propertyName != null) {
            checkNotBlank(propertyName, "The bean property name must not be empty, or whitespace.");
        }
    }


    // Conversions ************************************************************

    public ValueModelBindingBuilder formatted(Format format) {
        return new ValueModelBindingBuilderImpl(
                ConverterFactory.createStringConverter(getValueModel(), format),
                getPropertyName());
    }


//    public ValueModelBindingBuilder buffered(ValueModel triggerChannel) {
//        return new ValueModelBindingBuilderImpl(
//                new BufferedValueModel(valueModel, triggerChannel));
//    }
//
//
//    public ValueModelBindingBuilder delayed(int delayInMs) {
//        return new ValueModelBindingBuilderImpl(
//                new DelayedWriteValueModel(valueModel, delayInMs));
//    }


    // Selections *************************************************************

    public <E> SelectionInListBindingBuilder asSelectionIn(E[] array) {
        checkNotNull(array, "The array must not be null.");
        SelectionInList<E> selectionInList = new SelectionInList<E>(array, getValueModel());
        return new SelectionInListBindingBuilderImpl(selectionInList, getPropertyName());
    }


    public <E> SelectionInListBindingBuilder asSelectionIn(List<E> list) {
        checkNotNull(list, "The List must not be null.");
        SelectionInList<E> selectionInList = new SelectionInList<E>(list, getValueModel());
        return new SelectionInListBindingBuilderImpl(selectionInList, getPropertyName());
    }


    public <E> SelectionInListBindingBuilder asSelectionIn(ListModel listModel) {
        checkNotNull(listModel, "The ListModel must not be null.");
        SelectionInList<E> selectionInList = new SelectionInList<E>(listModel, getValueModel());
        return new SelectionInListBindingBuilderImpl(selectionInList, getPropertyName());
    }


    // Bindings ***************************************************************

    public void to(AbstractButton toggleButton) {
        checkNotNull(toggleButton, "The toggle button must not be null.");
        Bindings.bind(toggleButton, valueModel);
        setValidationMessageKey(toggleButton);
    }


    public void to(AbstractButton toggleButton, Object selectedValue, Object deselectedValue) {
        checkNotNull(toggleButton, "The toggle button must not be null.");
        Bindings.bind(toggleButton, valueModel, selectedValue, deselectedValue);
        setValidationMessageKey(toggleButton);
    }


    public void to(AbstractButton toggleButton, Object choice) {
        checkNotNull(toggleButton, "The toggle button must not be null.");
        Bindings.bind(toggleButton, valueModel, choice);
        setValidationMessageKey(toggleButton);
    }


//    public void to(JColorChooser colorChooser) {
//        checkNotNull(colorChooser, "The color chooser must not be null.");
//        Bindings.bind(colorChooser, valueModel);
//    }


    public void to(JFormattedTextField formattedTextField) {
        checkNotNull(formattedTextField, "The formatted text field must not be null.");
        Bindings.bind(formattedTextField, valueModel);
        setValidationMessageKey(formattedTextField);
    }


    public void to(JLabel label) {
        checkNotNull(label, "The label must not be null.");
        Bindings.bind(label, valueModel);
    }


    public void to(JTextArea textArea) {
        to(textArea, Commit.ON_FOCUS_LOST);
    }


    public void to(JTextArea textArea, Commit commitType) {
        checkNotNull(textArea, "The text area must not be null.");
        checkNotNull(commitType, "The commit type must not be null.");
        Bindings.bind(textArea, valueModel, commitType == Commit.ON_FOCUS_LOST);
        setValidationMessageKey(textArea);
    }


    public void to(JTextField textField) {
        if (textField instanceof JFormattedTextField) {
            to((JFormattedTextField) textField);
        } else {
            to(textField, Commit.ON_FOCUS_LOST);
        }
    }


    public void to(JTextField textField, Commit commitType) {
        checkNotNull(textField, "The text field must not be null.");
        checkNotNull(commitType, "The commit type must not be null.");
        checkArgument(!(textField instanceof JFormattedTextField),
                "For JFormattedTextField use method #to(JFormattedTextField)");
        Bindings.bind(textField, valueModel, commitType == Commit.ON_FOCUS_LOST);
        setValidationMessageKey(textField);
    }


    // Subclass Utils *********************************************************

    /**
     * @return this builder's ValueModel
     */
    protected final ValueModel getValueModel() {
        return valueModel;
    }


    /**
     * @return the property name - if any - that has been used to create
     *    this builder,
     *    e.g. in <tt>binder.bindBeanProperty(<b>"title"</b>).to(...)</tt>
     */
    protected final String getPropertyName() {
        return propertyName;
    }


    /**
     * Sets this builder's property name - if any - as the component's
     * validation message key.
     *
     * @param comp    the component where the validation message key shall be set
     */
    protected final void setValidationMessageKey(JComponent comp) {
        if (getPropertyName() != null) {
            BinderUtils.setValidationMessageKey(comp, getPropertyName());
        }
    }


}
