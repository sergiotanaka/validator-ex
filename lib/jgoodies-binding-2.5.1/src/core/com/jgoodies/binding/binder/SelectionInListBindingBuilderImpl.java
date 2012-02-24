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

import static com.jgoodies.common.base.Preconditions.checkNotBlank;
import static com.jgoodies.common.base.Preconditions.checkNotNull;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;



/**
 * A binding builder that holds a SelectionInList
 * that can be bound to combo boxes, lists, and tables.<p>
 *
 * <strong>Examples:</strong>
 * <pre>
 * binder.bind(countrySelectionInList).to(countryCombo);
 * binder.bind(countrySelectionInList).to(countryFilterCombo, "none");
 * binder.bind(contactSelectionInList).to(contactList);
 * binder.bind(contactSelectionInList).to(contactTable);
 * </pre>
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.2 $
 *
 * @since 2.3
 */
public class SelectionInListBindingBuilderImpl implements SelectionInListBindingBuilder {


    private final SelectionInList<?> selectionInList;

    /**
     * An optional property name that has been used to create
     * the SelectionInList. Used to set a validation message key for combo boxes.
     */
    private final String propertyName;


    // Instance Creation ******************************************************

    /**
     * Creates a SelectionInListBindingBuilderImpl for the given selection in list.
     *
     * @param selectionInList    provides both the data and the selection
     *
     * @throws NullPointerException if {@code selectionInList} is {@code null}
     */
    public SelectionInListBindingBuilderImpl(SelectionInList<?> selectionInList) {
        this(selectionInList, null);
    }


    /**
     * Creates a SelectionInListBindingBuilderImpl for the given selection in list.
     *
     * @param selectionInList    provides both the data and the selection
     * @param propertyName       an optional bean property name that has
     *     been used to create {@code selectionInList}. Used to set
     *     a validation message key for combo boxes.
     *
     * @throws NullPointerException
     *     if {@code selectionInList} is {@code null}
     * @throws IllegalArgumentException
     *     if {@code propertyName} is empty or whitespace
     */
    public SelectionInListBindingBuilderImpl(
            SelectionInList<?> selectionInList,
            String propertyName) {
        this.selectionInList =
            checkNotNull(selectionInList, "The SelectionInList must no be null.");
        this.propertyName = propertyName;
        if (propertyName != null) {
            checkNotBlank(propertyName, "The bean property name must not be empty, or whitespace.");
        }
    }


    // SelectionInListBindingBuilder Implementation ***************************

    public void to(JComboBox comboBox) {
        Bindings.bind(comboBox, selectionInList);
        setValidationMessageKey(comboBox);
    }


    public void to(JComboBox comboBox, String nullText) {
        Bindings.bind(comboBox, selectionInList, nullText);
        setValidationMessageKey(comboBox);
    }


    public void to(JList list) {
        Bindings.bind(list, selectionInList);
    }


    public void to(JTable table) {
        Bindings.bind(table, selectionInList);
    }


    // Subclass Utils *********************************************************

    /**
     * @return this builder's SelectionInList
     */
    protected final SelectionInList getSelectionInList() {
        return selectionInList;
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

