/*
 * Copyright (c) 2003-2012 JGoodies Karsten Lentzsch. All Rights Reserved.
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

package com.jgoodies.validation.util;

import com.jgoodies.common.bean.AbstractBean;
import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;

/**
 * An abstract class that minimizes the effort required to implement
 * the {@link ValidationResultModel} interface. It provides a property
 * change support
 * behavior to add and remove methods
 * to convert boolean, double, float, int, and long to their
 * corresponding Object values.<p>
 *
 * Subclasses must implement {@code getResult()} and
 * {@code setResult(ValidationResult)} to get and set
 * the observable validation result. #getResult always returns a
 * non-null result, #setResult accepts only non-null results.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.11 $
 *
 * @see com.jgoodies.validation.util.DefaultValidationResultModel
 *
 * @since 1.0.1
 */
public abstract class AbstractValidationResultModel extends AbstractBean implements ValidationResultModel {


    // Accessors **************************************************************

    /**
     * Looks up and returns the severity of the validation result,
     * one of error, warning, or {@code null}.
     *
     * @return the severity of the validation result
     */
    @Override
	public final Severity getSeverity() {
        return getResult().getSeverity();
    }


    /**
     * Checks and answers whether the validation result has errors.
     *
     * @return true if the validation result has errors, false otherwise
     */
    @Override
	public final boolean hasErrors() {
        return getResult().hasErrors();
    }


    /**
     * Checks and answers whether the validation result has messages.
     *
     * @return true if the validation result has messages, false otherwise
     */
    @Override
	public final boolean hasMessages() {
        return getResult().hasMessages();
    }


    // Convenience Behavior ***************************************************

    /**
     * Notifies all registered listeners about changes of the result itself
     * and the properties for severity, errors and messages. Useful to fire
     * all changes in a #setResult implementation.
     *
     * @param oldResult  the old validation result
     * @param newResult  the new validation result
     *
     * @throws NullPointerException if the old or new result is {@code null}
     *
     * @see #setResult(ValidationResult)
     * @see ValidationResultModelContainer#setResult(ValidationResult)
     */
    protected final void firePropertyChanges(
            ValidationResult oldResult, ValidationResult newResult) {
        Severity oldSeverity = oldResult.getSeverity();
        boolean oldErrors    = oldResult.hasErrors();
        boolean oldMessages  = oldResult.hasMessages();
        Severity newSeverity = newResult.getSeverity();
        boolean newErrors    = newResult.hasErrors();
        boolean newMessages  = newResult.hasMessages();
        firePropertyChange(PROPERTY_RESULT,   oldResult,   newResult);
        firePropertyChange(PROPERTY_ERRORS,   oldErrors,   newErrors);
        firePropertyChange(PROPERTY_MESSAGES, oldMessages, newMessages);
        firePropertyChange(PROPERTY_SEVERITY, oldSeverity, newSeverity);
    }


}
