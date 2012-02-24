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

package com.jgoodies.validation.extras;

import static com.jgoodies.common.base.Preconditions.checkArgument;
import static com.jgoodies.common.base.Preconditions.checkNotNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;


/**
 * An implementation of {@link ValidationResultModel} that wraps another
 * ValidationResultModel to limit the number of reported
 * {@link com.jgoodies.validation.ValidationMessage}s.<p>
 *
 * <strong>Note:</strong> This class is not yet part of the binary Validation
 * library; it comes with the Validation distributions as an extra.
 * <strong>The API is work in progress and may change without notice;
 * this class may even be completely removed from future distributions.</strong>
 * If you want to use this class, you may consider copying it into
 * your code base.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.17 $
 *
 * @see com.jgoodies.validation.ValidationResultModel
 */
public final class LimitedValidationResultModel extends DefaultValidationResultModel {

    /**
     * Holds the upper bound for the number of messages in
     * ValidationResults this model reports.
     */
    private final int limit;


    // Instance Creation ******************************************************

    /**
     * Constructs a {@code LimitedValidationResultModel}
     * on the given ValidationResultModel.
     *
     * @param model    the underlying model that provides all validation messages
     * @param limit    the upper bound for the number of messages this model returns
     */
    public LimitedValidationResultModel(ValidationResultModel  model, int limit) {
        checkNotNull(model, "The ValidationResultModel must not be null.");
        checkArgument(limit > 0, "The limit must be positive.");
        this.limit = limit;
        model.addPropertyChangeListener(ValidationResultModel.PROPERTY_RESULT, new ValidationResultChangeHandler());
    }


    // Implementing the Limitation ********************************************

    private ValidationResult limitedResult(ValidationResult result) {
        return result.isEmpty()
            ? ValidationResult.EMPTY
            : result.subResult(0, Math.min(limit, result.size()));
    }


    // Helper Class ***********************************************************

    /**
     * Updates the limited validation result if the underlying model changes.
     */
    private final class ValidationResultChangeHandler implements PropertyChangeListener {

        /**
         * The underlying validation result model has changed.
         * Updates this model's validation result to a limited version
         * of the new full validation result.
         *
         * @param evt    the property change event to handle
         */
        @Override
		public void propertyChange(PropertyChangeEvent evt) {
            ValidationResult result = (ValidationResult) evt.getNewValue();
            setResult(limitedResult(result));
        }

    }


}
