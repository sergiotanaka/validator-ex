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

import static com.jgoodies.common.base.Preconditions.checkNotNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;

import com.jgoodies.common.bean.AbstractBean;



/**
 * An abstract wrapper for ValueModels that provides ComponentModel features.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.15 $
 *
 * @since 2.4
 */
public abstract class AbstractWrappedValueModel extends AbstractValueModel
    implements ComponentValueModel {


    // Instance Fields ********************************************************

    /**
     * Holds the wrapped ValueModel that is used to read and write value.
     */
    private final ValueModel wrappee;

    /**
     * Provides the component properties: enabled, visible, editable.
     * Is either the wrapped ValueModel or a simple bean.
     */
    private final ComponentModel componentPropertyProvider;

    private final PropertyChangeListener valueChangeHandler;
    private final PropertyChangeListener componentPropertyChangeHandler;


    // Instance Creation ******************************************************

    /**
     * Constructs a DefaultComponentValueModel that wraps the given ValueModel
     * and adds {@link ComponentModel} Properties.
     *
     * @param wrappee   the underlying or wrapped ValueModel
     */
    public AbstractWrappedValueModel(ValueModel wrappee) {
        this.wrappee = checkNotNull(wrappee, "The wrapped model must not be null.");
        this.componentPropertyProvider = wrappee instanceof ComponentModel
                ? (ComponentModel) wrappee
                : new SimpleComponentModel();
        this.valueChangeHandler = createValueChangeHandler();
        this.componentPropertyChangeHandler = new ComponentPropertyChangeHandler();
        wrappee.addValueChangeListener(valueChangeHandler);
        componentPropertyProvider.addPropertyChangeListener(componentPropertyChangeHandler);
    }


    // ComponentModel Implementation ******************************************

    /**
     * Returns if this model represents an enabled or disabled component state.
     *
     * @return true for enabled, false for disabled
     */
    public boolean isEnabled() {
        return componentPropertyProvider.isEnabled();
    }


    /**
     * Enables or disabled this model, which in turn
     * will enable or disable all Swing components bound to this model.
     *
     * @param b true to enable, false to disable.
     */
    public void setEnabled(boolean b) {
        componentPropertyProvider.setEnabled(b);
    }


    /**
     * Returns if this model represents the visible or invisible component state.
     *
     * @return true for visible, false for invisible
     */
    public boolean isVisible() {
        return componentPropertyProvider.isVisible();
    }


    /**
     * Sets this model state to visible or invisible, which in turn
     * will make all Swing components bound to this model visible or invisible.
     *
     * @param b    true for visible, false for invisible
     */
    public void setVisible(boolean b) {
        componentPropertyProvider.setVisible(b);
    }


    /**
     * Returns if this model represents the editable or non-editable
     * text component state.
     *
     * @return true for editable, false for non-editable
     */
    public boolean isEditable() {
        return componentPropertyProvider.isEditable();
    }


    /**
     * Sets this model state to editable or non-editable, which in turn will
     * make all text components bound to this model editable or non-editable.
     *
     * @param b    true for editable, false for non-editable
     */
    public void setEditable(boolean b) {
        componentPropertyProvider.setEditable(b);
    }


    // Misc *******************************************************************

    /**
     * Removes the internal change handlers from the wrapped value model.
     * The listener has been registered during construction. Useful to avoid
     * memory leaks, if the wrappee lives much longer than this wrapper.
     * As an alternative you can use event listener lists in your ValueModels
     * that implement references with {@link WeakReference}.<p>
     *
     * This model must not be used anymore once #release has been called.<p>
     *
     * Subclasses that override this method must call this super implementation.
     */
    public void release() {
        wrappee.removeValueChangeListener(valueChangeHandler);
        componentPropertyProvider.removePropertyChangeListener(componentPropertyChangeHandler);
    }


    // Abstract Behavior ******************************************************

    abstract protected PropertyChangeListener createValueChangeHandler();


    // Default Access *********************************************************

    protected final ValueModel getWrappee() {
        return wrappee;
    }


    // Event Handling *********************************************************

    /**
     * Forwards component changes in the wrappee to listeners of this wrapper.
     */
    private final class ComponentPropertyChangeHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            if (  propertyName == null
                || propertyName.equals(PROPERTY_ENABLED)
                || propertyName.equals(PROPERTY_VISIBLE)
                || propertyName.equals(PROPERTY_EDITABLE)) {
                firePropertyChange(propertyName, evt.getOldValue(), evt.getNewValue());
            }
        }
    }


    // Helper Class ***********************************************************

    private static final class SimpleComponentModel extends AbstractBean implements ComponentModel {

        private boolean enabled;
        private boolean visible;
        private boolean editable;


        SimpleComponentModel() {
            this.enabled = true;
            this.visible = true;
            this.editable = true;
        }


        public boolean isEnabled() {
            return enabled;
        }


        public void setEnabled(boolean b) {
            boolean oldEnabled = isEnabled();
            enabled = b;
            firePropertyChange(PROPERTY_ENABLED, oldEnabled, b);
        }


        public boolean isVisible() {
            return visible;
        }


        public void setVisible(boolean b) {
            boolean oldVisible = isVisible();
            visible = b;
            firePropertyChange(PROPERTY_VISIBLE, oldVisible, b);
        }


        public boolean isEditable() {
            return editable;
        }


        public void setEditable(boolean b) {
            boolean oldEditable = isEditable();
            editable = b;
            firePropertyChange(PROPERTY_EDITABLE, oldEditable, b);
        }


    }

}
