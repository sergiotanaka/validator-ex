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

package com.jgoodies.validation.tests;

import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.message.SimpleValidationMessage;

/**
 * Consists only of static fields for prepared ValidationResults
 * used by different Validation test cases. The result names
 * are an encoded form of their content:
 * {@code E1} is a result with just error1,
 * {@code E1W2} is a result with error1 and warning2,
 * {@code E1E2W1} is a result error1, error2 and warning1.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.8 $
 */
public final class ValidationResults {

    public static final Object KEY1;
    public static final Object KEY2;
    public static final Object KEY3;

    static final ValidationMessage ERROR1;
    static final ValidationMessage ERROR2;
    static final ValidationMessage ERROR3;

    public static final ValidationResult E1;
    public static final ValidationResult E2;
    public static final ValidationResult W1;
    public static final ValidationResult W2;

    public static final ValidationResult E1E1;
    public static final ValidationResult E1E2;
    public static final ValidationResult E1W1;
    public static final ValidationResult E1W2;

    public static final ValidationResult E2E1;
    public static final ValidationResult E2E2;
    public static final ValidationResult E2W1;
    public static final ValidationResult E2W2;

    public static final ValidationResult W1E1;
    public static final ValidationResult W1E2;
    public static final ValidationResult W1W1;
    public static final ValidationResult W1W2;

    public static final ValidationResult W2E1;
    public static final ValidationResult W2E2;
    public static final ValidationResult W2W1;
    public static final ValidationResult W2W2;

    public static final ValidationResult E1E1E1;
    public static final ValidationResult E1E1W1;
    public static final ValidationResult E1W1E1;
    public static final ValidationResult E1W1W1;
    public static final ValidationResult W1E1E1;
    public static final ValidationResult W1E1W1;
    public static final ValidationResult W1W1E1;
    public static final ValidationResult W1W1W1;

    public static final ValidationResult E1E2E3;

    // Initialization *********************************************************

    static {
        KEY1 = "key1";
        KEY2 = "key2";
        KEY3 = "key3";

        ERROR1 = new SimpleValidationMessage(
                "Validation error1", Severity.ERROR, KEY1);
        ERROR2 = new SimpleValidationMessage(
                "Validation error2", Severity.ERROR, KEY2);
        ERROR3 = new SimpleValidationMessage(
                "Validation error3", Severity.ERROR, KEY3);
        ValidationMessage warning1 = new SimpleValidationMessage(
                "Validation warning1", Severity.WARNING, KEY1);
        ValidationMessage warning2 = new SimpleValidationMessage(
                "Validation warning2", Severity.WARNING, KEY2);

        E1 = createUnmodifiableResult(ERROR1);
        E2 = createUnmodifiableResult(ERROR2);
        W1 = createUnmodifiableResult(warning1);
        W2 = createUnmodifiableResult(warning2);

        // ---------------------------------

        E1E1 = createUnmodifiableResult(ERROR1, ERROR1);
        E1E2 = createUnmodifiableResult(ERROR1, ERROR2);
        E1W1 = createUnmodifiableResult(ERROR1, warning1);
        E1W2 = createUnmodifiableResult(ERROR1, warning2);

        E2E1 = createUnmodifiableResult(ERROR2, ERROR1);
        E2E2 = createUnmodifiableResult(ERROR2, ERROR2);
        E2W1 = createUnmodifiableResult(ERROR2, warning1);
        E2W2 = createUnmodifiableResult(ERROR2, warning2);

        W1E1 = createUnmodifiableResult(warning1, ERROR1);
        W1E2 = createUnmodifiableResult(warning1, ERROR2);
        W1W1 = createUnmodifiableResult(warning1, warning1);
        W1W2 = createUnmodifiableResult(warning1, warning2);

        W2E1 = createUnmodifiableResult(warning2, ERROR1);
        W2E2 = createUnmodifiableResult(warning2, ERROR2);
        W2W1 = createUnmodifiableResult(warning2, warning1);
        W2W2 = createUnmodifiableResult(warning2, warning2);

        // ----------------------------------

        E1E1E1 = createUnmodifiableResult(ERROR1,   ERROR1,   ERROR1);
        E1E1W1 = createUnmodifiableResult(ERROR1,   ERROR1,   warning1);
        E1W1E1 = createUnmodifiableResult(ERROR1,   warning1, ERROR1);
        E1W1W1 = createUnmodifiableResult(ERROR1,   warning1, warning1);
        W1E1E1 = createUnmodifiableResult(warning1, ERROR1,   ERROR1);
        W1E1W1 = createUnmodifiableResult(warning1, ERROR1,   warning1);
        W1W1E1 = createUnmodifiableResult(warning1, warning1, ERROR1);
        W1W1W1 = createUnmodifiableResult(warning1, warning1, warning1);

        E1E2E3 = createUnmodifiableResult(ERROR1,   ERROR2,   ERROR3);
    }


    // Overridden Constructor *************************************************

    private ValidationResults() {
        // Override default constructor; prevents instantiation.
    }


    // Helper Code ************************************************************

    private static ValidationResult createUnmodifiableResult(ValidationMessage message) {
        ValidationResult result = new ValidationResult();
        result.add(message);
        return ValidationResult.unmodifiableResult(result);
    }

    private static ValidationResult createUnmodifiableResult(
            ValidationMessage message1,
            ValidationMessage message2) {
        ValidationResult result = new ValidationResult();
        result.add(message1);
        result.add(message2);
        return ValidationResult.unmodifiableResult(result);
    }

    private static ValidationResult createUnmodifiableResult(
            ValidationMessage message1,
            ValidationMessage message2,
            ValidationMessage message3) {
        ValidationResult result = new ValidationResult();
        result.add(message1);
        result.add(message2);
        result.add(message3);
        return ValidationResult.unmodifiableResult(result);
    }



}
