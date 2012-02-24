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

import com.jgoodies.common.bean.ObservableBean2;



/**
 * Describes bound properties for the frequently used JComponent state
 * <em>enabled</em>,<em>visible</em> and JTextComponent state <em>editable</em>.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.15 $
 *
 * @see ComponentValueModel
 *
 * @since 2.4
 */
public interface ComponentModel extends ObservableBean2 {

    // Names of the Bean Properties *******************************************

    /**
     * The name of the property used to synchronize
     * this model with the <em>enabled</em> property of JComponents.
     */
    String PROPERTY_ENABLED  = "enabled";

    /**
     * The name of the property used to synchronize
     * this model with the <em>visible</em> property of JComponents.
     */
    String PROPERTY_VISIBLE  = "visible";

    /**
     * The name of the property used to synchronize
     * this model with the <em>editable</em> property of JTextComponents.
     */
    String PROPERTY_EDITABLE = "editable";


    // Properties *************************************************************

    /**
     * Returns if this model represents an enabled or disabled component state.
     *
     * @return true for enabled, false for disabled
     */
    boolean isEnabled();


    /**
     * Enables or disabled this model, which in turn
     * will enable or disable all Swing components bound to this model.
     *
     * @param b true to enable, false to disable.
     */
    void setEnabled(boolean b);


    /**
     * Returns if this model represents the visible or invisible component state.
     *
     * @return true for visible, false for invisible
     */
    boolean isVisible();


    /**
     * Sets this model state to visible or invisible, which in turn
     * will make all Swing components bound to this model visible or invisible.
     *
     * @param b    true for visible, false for invisible
     */
    void setVisible(boolean b);


    /**
     * Returns if this model represents the editable or non-editable
     * text component state.
     *
     * @return true for editable, false for non-editable
     */
    boolean isEditable();


    /**
     * Sets this model state to editable or non-editable, which in turn will
     * make all text components bound to this model editable or non-editable.
     *
     * @param b    true for editable, false for non-editable
     */
    void setEditable(boolean b);


}
