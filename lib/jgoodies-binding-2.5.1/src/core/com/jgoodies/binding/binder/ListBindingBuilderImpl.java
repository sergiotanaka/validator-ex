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

import static com.jgoodies.common.base.Preconditions.checkNotNull;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.jgoodies.binding.adapter.Bindings;



/**
 * Holds a ListModel and ListSelectionModel that can be bound to a list or table.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.2 $
 *
 * @since 2.3
 */
public class ListBindingBuilderImpl implements ListBindingBuilder {

    private final ListModel listModel;
    private final ListSelectionModel listSelectionModel;


    // Instance Creation ******************************************************

    /**
     * Creates a ListBindingBuilderImpl for the given list model and list
     * selection model.
     *
     * @param listModel            provides the data to be bound to a component
     * @param listSelectionModel   provides the selection model to be bound
     *    to a component
     * @throws NullPointerException if {@code listModel}
     *     or {@code listSelectionModel} is {@code null}
     */
    public ListBindingBuilderImpl(ListModel listModel, ListSelectionModel listSelectionModel) {
        this.listModel = checkNotNull(listModel, "The ListModel must not be null.");
        this.listSelectionModel = checkNotNull(listSelectionModel, "The ListSelectionModel must not be null.");
    }


    // ListBindingBuilder Implementation **************************************

    public void to(JTable table)  {
        Bindings.bind(table, listModel, listSelectionModel);
    }


    public void to(JList list) {
        list.setModel(listModel);
        list.setSelectionModel(listSelectionModel);
    }


}

