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

package com.jgoodies.validation;

import com.jgoodies.common.bean.ObservableBean2;


/**
 * Describes a model that holds a {@link ValidationResult} and provides bound
 * read-only properties for the result, severity, error and messages state.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.10 $
 *
 * @see     com.jgoodies.validation.util.DefaultValidationResultModel
 *
 * @since 1.1
 */
public interface ValidationResultModel extends ObservableBean2 {

    /**
     * The name of the bound property for the validation result.
     *
     * @see #getResult()
     */
    String PROPERTY_RESULT = "result";


    /**
     * The name of the bound property for the validation result severity.
     *
     * @see #getSeverity()
     */
    String PROPERTY_SEVERITY = "severity";


    /**
     * The name of the bound property that indicates whether there are errors.
     *
     * @see #hasErrors()
     */
    String PROPERTY_ERRORS = "errors";


    /**
     * The name of the bound property that indicates whether there are messages.
     *
     * @see #hasMessages()
     */
    String PROPERTY_MESSAGES = "messages";


    // Accessors **************************************************************

    /**
     * Returns this model's validation result which must be non-null.
     *
     * @return the current validation result
     *
     * @see #setResult(ValidationResult)
     */
    ValidationResult getResult();


    /**
     * Sets a new non-null validation result and notifies all registered
     * listeners, if the result changed. This is typically invoked at the end
     * of the {@code #validate()} method.<p>
     *
     * Implementors shall throw a NullPointerException if the new result
     * is <code>null</code>. It is recommended that implementors prevent
     * modifications of the given ValidationResult by using an internal 
     * and unmodifiable copy. 
     *
     * @param newResult  the validation result to be set
     *
     * @see #getResult()
     */
    void setResult(ValidationResult newResult);


    /**
     * Looks up and returns the Severity of this model's validation result,
     * one of {@code Severity.ERROR}, {@code Severity.WARNING},
     * or {@code Severity.OK}.
     *
     * @return the severity of this model's validation result
     *
     * @see #hasErrors()
     * @see #hasMessages()
     */
    Severity getSeverity();


    /**
     * Checks and answers whether this model's validation result has errors.
     *
     * @return true if the validation result has errors, false otherwise
     *
     * @see #getSeverity()
     * @see #hasMessages()
     */
    boolean hasErrors();


    /**
     * Checks and answers whether this model's validation result has messages.
     *
     * @return true if the validation result has messages, false otherwise
     *
     * @see #getSeverity()
     * @see #hasErrors()
     */
    boolean hasMessages();


}
