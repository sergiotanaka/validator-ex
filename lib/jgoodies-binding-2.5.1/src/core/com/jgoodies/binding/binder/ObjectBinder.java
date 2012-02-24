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

import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;


/**
 * Describes a binder that can build action bindings from Actions,
 * data bindings from ValueModels, and list bindings from
 * ListModel/ListSelectionModel or a SelectionInList.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.1 $
 *
 * @since 2.3
 */
public interface ObjectBinder {


    /**
     * Creates and returns a binding builder that manages an Action that
     * can be operated on and that can be bound to a button or text field.<p>
     *
     * <strong>Example:</strong>
     * <tt>binder.<b>bind</b>(editAction).to(editButton);</tt>
     *
     * @param action     the Action to be bound
     *
     * @return the binding builder that holds the Action
     *
     * @throws NullPointerException  if {@code action} if {@code null}
     */
    ActionBindingBuilder bind(Action action);


    /**
     * Creates and returns a binding builder that manages a ListModel
     * plus ListSelectionModel that can be bound to a list or table.<p>
     *
     * <strong>Example:</strong>
     * <tt>binder<b>.bind</b>(albumListModel, albumListSelectionModel).to(albumTable);</tt>
     *
     * @param listModel            the data model
     * @param listSelectionModel   the selection model
     *
     * @return the binding builder that holds the data and selection model
     *
     * @throws NullPointerException if {@code listModel}
     *     or {@code listSelectionModel} is {@code null}
     */
    ListBindingBuilder bind(ListModel listModel, ListSelectionModel listSelectionModel);


    /**
     * Creates and returns a binding builder that manages a SelectionInList
     * that can be bound to a list, table, or combo box.<p>
     *
     * <strong>Example:</strong>
     * <tt>binder<b>.bind</b>(albumSelectionInList).to(albumTable);</tt>
     *
     * @param selectionInList     provides both the data and selection
     *
     * @return the binding builder that holds the converted bean property
     *
     * @throws NullPointerException if {@code selectionInList} is {@code null}
     */
    SelectionInListBindingBuilder bind(SelectionInList<?> selectionInList);


    /**
     * Creates and returns a binding builder that manages a ValueModel
     * that can be operated on and that can be bound to a component.<p>
     *
     * <strong>Example:</strong>
     * <tt>binder<b>.bind</b>(aValueModel).to(aTextField);</tt>
     *
     * @param valueModel     the ValueModel to manage
     *
     * @return the binding builder that holds the ValueModel
     *
     * @throws NullPointerException if {@code valueModel} is {@code null}
     */
    ValueModelBindingBuilder bind(ValueModel valueModel);


}

