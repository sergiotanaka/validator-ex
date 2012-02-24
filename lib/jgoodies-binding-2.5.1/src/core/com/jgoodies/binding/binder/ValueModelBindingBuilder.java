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

import java.text.Format;
import java.util.List;

import javax.swing.*;

import com.jgoodies.binding.adapter.Bindings;



/**
 * Describes a binding builder that holds a ValueModel that can be bound
 * to a variety of components. It also describes methods that operate
 * on the ValueModel (conversion) or create a SelectionInListBuilder
 * that in turn can be bound to combo boxes, lists, and tables.
 *
 * <strong>Examples:</strong>
 * <pre>
 * binder.bindBeanProperty("classical").to(classicalBox);
 * binder.bindBeanProperty("title")    .to(titleField);
 * binder.bind(resultCountValueModel)
 *       .converted(aTableHeaderFormat).to(tableHeaderLabel);
 * binder.bindBeanProperty("country")
 *       .asSelectionIn(COUNTRY_LIST)  .to(countryCombo);
 * </pre>
 * @author  Karsten Lentzsch
 * @version $Revision: 1.12 $
 *
 * @since 2.3
 */
public interface ValueModelBindingBuilder {


    /**
     * Describes the commit types used for text field and text area bindings.
     */
    public enum Commit {
        /** Value changes are committed on focus lost. */
        ON_FOCUS_LOST,
        /** Value changes are committed on every key types. */
        ON_KEY_TYPED
    }


    // Conversions ************************************************************

    /**
     * Wraps this builder's ValueModel with a string converter and
     * creates and returns another ValueModelBindingBuilder with the wrapped
     * ValueModel.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("count").<b>converted</b>(percentFormat).to(percentField);</tt>
     *
     * @param format    implements the String conversion via #format and #parse
     *
     * @return a ValueModelBindingBuilder on a converter that wraps
     *    this builder's String ValueModel
     *
     * @throws NullPointerException if {@code format} is {@code null}
     */
    ValueModelBindingBuilder formatted(Format format);


//  ValueModelBindingBuilder buffered(ValueModel triggerChannel);
//
//  ValueModelBindingBuilder delayed(int delayInMs);


    // Selections *************************************************************

    /**
     * Creates and returns a SelectionInListBindingBuilder on a SelectionInList
     * with this builder's ValueModel as selection holder and the given array
     * as list.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("country").<b>asSelectionIn</b>(COUNTRIES).to(countryCombo);</tt>
     *
     * @param array    the list data for the SelectionInList
     * @param <E>      the type of the list elements
     *
     * @return a SelectionInListBindingBuilder on a SelectionInList where
     *    this builder's ValueModel is the selection holder
     *
     * @throws NullPointerException if {@code array} is {@code null}
     */
    <E> SelectionInListBindingBuilder asSelectionIn(E[] array);


    /**
     * Creates and returns a SelectionInListBindingBuilder on a SelectionInList
     * with this builder's ValueModel as selection holder and the given
     * {@code list} as list elements.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("country").<b>asSelectionIn</b>(COUNTRIES).to(countryCombo);</tt>
     *
     * @param list    the list data for the SelectionInList
     * @param <E>     the type of the list elements
     *
     * @return a SelectionInListBindingBuilder on a SelectionInList where
     *    this builder's ValueModel is the selection holder
     *
     * @throws NullPointerException if {@code list} is {@code null}
     */
    <E> SelectionInListBindingBuilder asSelectionIn(List<E> list);


    /**
     * Creates and returns a SelectionInListBindingBuilder on a SelectionInList
     * with this builder's ValueModel as selection holder and the given
     * ListModel as list data provider.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("country").<b>asSelectionIn</b>(COUNTRIES).to(countryCombo);</tt>
     *
     * @param listModel    the list data for the SelectionInList
     * @param <E>          the type of the list elements
     *
     * @return a SelectionInListBindingBuilder on a SelectionInList where
     *    this builder's ValueModel is the selection holder
     *
     * @throws NullPointerException if {@code listModel} is {@code null}
     */
    <E> SelectionInListBindingBuilder asSelectionIn(ListModel listModel);


    // Bindings ***************************************************************

    /**
     * Binds this builder's ValueModel to the given toggle button,
     * for example a check box.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("classical").<b>to</b>(classicalBox);</tt>
     *
     * @param toggleButton   the button to be bound to this builder's ValueModel
     *
     * @throws NullPointerException if {@code checkBox} is {@code null}
     *
     * @see Bindings#bind(AbstractButton, com.jgoodies.binding.value.ValueModel)
     */
    void to(AbstractButton toggleButton);


    /**
     * Binds this builder's ValueModel to the given toggle button,
     * for example a check box.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("classical").<b>to</b>(classicalBox, CLASSICAL, PLAIN);</tt>
     *
     * @param toggleButton   the button to be bound to this builder's ValueModel
     * @param selectedValue   the model's value if the button is selected
     * @param deselectedValue the model's value if the button is not selected
     *
     * @throws NullPointerException if {@code checkBox} is {@code null}
     *
     * @see Bindings#bind(AbstractButton, com.jgoodies.binding.value.ValueModel)
     */
    void to(AbstractButton toggleButton, Object selectedValue, Object deselectedValue);


    /**
     * Binds this builder's ValueModel to the given toggle button
     * (radio button style) that is selected, if and only if the model's value
     * equals the given {@code choice}.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("alignment").<b>to</b>(alignmentButton, Alignment.LEFT);</tt>
     *
     * @param toggleButton  the button to be bound to this builder's ValueModel
     * @param choice        the model value where {@code radioButton} shall be selected
     *
     * @throws NullPointerException if {@code radioButton} is {@code null}
     *
     * @see Bindings#bind(AbstractButton, com.jgoodies.binding.value.ValueModel, Object)
     */
    void to(AbstractButton toggleButton, Object choice);


    // Due to a bug in Java 1.4.2, Java 5 and Java 6, the following
    // binding has been commented. To work correctly, the JColorChooser
    // Needs a ColorSelectionModel at instance creation time.
    // The bug is in BasicColorChooserUI
    // that doesn't listen to color selection model changes.
    // This is required to update the color preview panel.
    // But the BasicColorChooserUI registers a preview listener
    // with the initial color selection model.
//    /**
//     * Binds this builder's ValueModel to the given color chooser.<p>
//     *
//     * <strong>Example:</strong><br>
//     * <tt>binder.bindBeanProperty("foreground").<b>to</b>(colorChooser);</tt>
//     *
//     * @param colorChooser   the color chooser to be bound to this builder's ValueModel
//     *
//     * @throws NullPointerException if {@code colorChooser} is {@code null}
//     *
//     * @see Bindings#bind(JColorChooser, com.jgoodies.binding.value.ValueModel)
//     */
//    void to(JColorChooser colorChooser);


    /**
     * Binds this builder's ValueModel to the given formatted text field.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("releaseDate").<b>to</b>(dateField);</tt>
     *
     * @param formattedTextField   the formatted text field to be bound to this builder's ValueModel
     *
     * @throws NullPointerException if {@code formattedTextField} is {@code null}
     *
     * @see Bindings#bind(JFormattedTextField, com.jgoodies.binding.value.ValueModel)
     */
    void to(JFormattedTextField formattedTextField);



    /**
     * Binds this builder's ValueModel to the given text label.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.binProperty("resultCount").converted(resultFormat).<b>to</b>(tableHeaderLabel);</tt>
     *
     * @param label   the label to be bound to this builder's ValueModel
     *
     * @throws NullPointerException if {@code label} is {@code null}
     *
     * @see Bindings#bind(JLabel, com.jgoodies.binding.value.ValueModel)
     */
    void to(JLabel label);


    /**
     * Binds this builder's ValueModel to the given text area and
     * commits text changes on focus lost.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("comment").<b>to</b>(commentArea);</tt>
     *
     * @param textArea
     *     the text area to be bound to this builder's ValueModel
     *
     * @throws NullPointerException if {@code textArea} is {@code null}
     *
     * @see Bindings#bind(JTextArea, com.jgoodies.binding.value.ValueModel)
     */
    void to(JTextArea textArea);


    /**
     * Binds this builder's ValueModel to the given text area using
     * the specified commit type.<p>
     *
     * <strong>Examples:</strong><br>
     * <tt>binder.bindBeanProperty("comment").<b>to</b>(commentArea, Commit.ON_KEY_TYPED);</tt>
     * <tt>binder.bindBeanProperty("comment").<b>to</b>(commentArea, Commit.ON_FOCUS_LOST);</tt>
     *
     * @param textArea
     *     the text area to be bound to this builder's ValueModel
     * @param commitType
     *     the commit type to be used,
     *     either {@code Commit.ON_KEY_TYPED} or {@code Commit.ON_FOCUS_LOST}
     *
     * @throws NullPointerException if {@code textArea}
     *     or {@code commitType} is {@code null}
     *
     * @see Bindings#bind(JTextArea, com.jgoodies.binding.value.ValueModel)
     */
    void to(JTextArea textArea, Commit commitType);


    /**
     * Binds this builder's ValueModel to the given text field and
     * commits text changes on focus lost.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bindBeanProperty("title").<b>to</b>(titleField);</tt>
     *
     * @param textField
     *     the text field to be bound to this builder's ValueModel
     *
     * @throws NullPointerException if {@code textField} is {@code null}
     *
     * @see Bindings#bind(JTextField, com.jgoodies.binding.value.ValueModel)
     */
    void to(JTextField textField);


    /**
     * Binds this builder's ValueModel to the given text field using
     * the specified commit type.<p>
     *
     * <strong>Examples:</strong><br>
     * <tt>binder.bindBeanProperty("title").<b>to</b>(titleField, Commit.ON_KEY_TYPED);</tt>
     * <tt>binder.bindBeanProperty("title").<b>to</b>(titleField, Commit.ON_FOCUS_LOST);</tt>
     *
     * @param textField
     *     the text field to be bound to this builder's ValueModel
     * @param commitType
     *     the commit type to be used,
     *     either {@code Commit.ON_KEY_TYPED} or {@code Commit.ON_FOCUS_LOST}
     *
     * @throws NullPointerException if {@code textField} or {@code commitType}
     *     is {@code null}
     *
     * @see Bindings#bind(JTextField, com.jgoodies.binding.value.ValueModel)
     */
    void to(JTextField textField, Commit commitType);


}

