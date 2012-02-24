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

import javax.swing.AbstractButton;
import javax.swing.JTextField;



/**
 * Describes a binding builder that holds an Action
 * that can be bound to buttons and text fields.<p>
 *
 * <strong>Examples:</strong>
 * <pre>
 * binder.bind(editAction)    .to(editButton);
 * binder.bind(searchAction)  .to(searchField);
 * binder.bindAction("Delete").to(deleteButton);
 * </pre>
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.2 $
 *
 * @since 2.3
 */
public interface ActionBindingBuilder {

    /**
     * Sets this builder's Action in the given button.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bind(editAction).<b>to</b>(editButton);</tt>
     *
     * @param button   the button where the Action is set
     *
     * @throws NullPointerException if {@code button} is {@code null}
     */
    void to(AbstractButton button);


    /**
     * Sets this builder's Action in the given text field.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bind(searchAction).<b>to</b>(searchField);</tt>
     *
     * @param textField    the field where the Action is set
     *
     * @throws NullPointerException if {@code textField} is {@code null}
     */
    void to(JTextField textField);


}

