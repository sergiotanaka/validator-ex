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

import junit.framework.TestCase;

import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.message.SimpleValidationMessage;

/**
 * A test case for class {@link ValidationResult}.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.17 $
 */
public final class ValidationResultTest extends TestCase {


    // Equals *****************************************************************

    /**
     * Verifies that two empty validation results are equal
     * even if they are not identical.
     */
    public static void testEqualityOnEmptyResults() {
        assertTrue("Two empty validation results are equal.",
                ValidationResult.EMPTY.equals(new ValidationResult()));
    }


    /**
     * Verifies that two different validation results are not equal.
     */
    public static void testEqualityOnDifferentResults() {
        assertFalse("An empty result is not equal to a non-empty result.",
                ValidationResult.EMPTY.equals(ValidationResults.E1));
        assertFalse("An result with an error is not equal to a result with a warning.",
                ValidationResults.E1.equals(ValidationResults.W1));
        assertFalse("An result with two errors is not equal to a result with three errors.",
                ValidationResults.E1E2.equals(ValidationResults.E1E2E3));
    }


    /**
     * Verifies that two different validation results are not equal.
     */
    public static void testEqualityOnEqualResults() {
        ValidationResult result1 = new ValidationResult();
        result1.addError("Error1");
        result1.addError("Error2");
        result1.addWarning("Warning1");

        ValidationResult result2 = new ValidationResult();
        result2.addAllFrom(result1);

        ValidationResult result3 = new ValidationResult();
        result3.addError("Error1");
        result3.addError("Error2");
        result3.addWarning("Warning1");

        assertEquals("A result R1 equals R2 if R1 is built using R1's messages.",
                result1, result2);

        assertEquals("Two results are equal if they contain equal simple messages.",
                result1, result3);
    }


    // Result State ***********************************************************
    /**
     * Verifies that a validation result that contains error messages
     * reports that it has errors.
     */
    public static void testHasErrors() {
        assertFalse("An empty result shall not report errors.",
                ValidationResult.EMPTY.hasErrors());

        assertTrue("An error result shall not report errors.",
                ValidationResults.E1.hasErrors());

        assertFalse("A warning result shall not report errors.",
                ValidationResults.W1.hasErrors());

        assertTrue("Mixed result1 shall report errors.",
                ValidationResults.E1W1.hasErrors());
        assertTrue("Mixed result2 shall report errors.",
                ValidationResults.W1E1.hasErrors());
    }

    /**
     * Verifies that a validation result that contains messages
     * reports that it has messages.
     */
    public static void testHasMessages() {
        assertFalse("An empty result shall not report messages.",
                ValidationResult.EMPTY.hasMessages());

        assertTrue("An error result shall report messages.",
                ValidationResults.E1.hasMessages());

        assertTrue("A warning result shall report messages.",
                ValidationResults.W1.hasMessages());

        assertTrue("Mixed result1 shall report messages.",
                ValidationResults.W1E1.hasMessages());
        assertTrue("Mixed result2 shall report messages.",
                ValidationResults.E1W1.hasMessages());
    }


    /**
     * Verifies that a validation result that contains warning messages
     * reports that it has warnings.
     */
    public static void testHasWarnings() {
        assertFalse("An empty result shall not report warnings.",
                ValidationResult.EMPTY.hasWarnings());

        assertTrue("A warning result shall not report warnings.",
                ValidationResults.W1.hasWarnings());

        assertFalse("An error result shall not report warnings.",
                ValidationResults.E1.hasWarnings());

        assertTrue("Mixed result1 shall report warnings.",
                ValidationResults.E1W1.hasWarnings());
        assertTrue("Mixed result2 shall report warnings.",
                ValidationResults.W1E1.hasWarnings());
    }


    // List Operations ********************************************************

    /**
     * Tests that all severities in the error only result are errors.
     */
    public static void testIteratable() {
        for (ValidationMessage message : ValidationResults.E1E2E3) {
            assertSame("The message type is error.",
                    Severity.ERROR, message.severity());
        }
    }


    public static void testSize() {
        assertEquals("Error result 2 has length 1.",
                1, ValidationResults.E2.size());
    }


    public static void testGet() {
        assertEquals("The first message in the error result 2 is the error 2.",
                ValidationResults.ERROR2, ValidationResults.E2.get(0));
    }


    public static void testContains() {
        assertTrue("The error result 2 contains error1.",
                ValidationResults.E2.contains(ValidationResults.ERROR2));
        assertFalse("The error result 2 does not contain error1.",
                ValidationResults.E2.contains(ValidationResults.ERROR1));
    }


    // Unmodifiable ***********************************************************

    public static void testIsUnmodifiable() {
        assertFalse("ValidationResult.EMPTY is unmodifiable.",
                ValidationResult.EMPTY.isModifiable());

        assertTrue("new ValidationResult() is modifiable.",
                new ValidationResult().isModifiable());

        ValidationResult result = new ValidationResult();
        result.addWarning("A warning");
        assertTrue("ValidationResults are initialized as modifiable.",
                result.isModifiable());
        assertFalse("ValidationResults can be turned into unmodifiable results.",
                ValidationResult.unmodifiableResult(result).isModifiable());
    }


    public static void testUnmodifiableResultIgnoresInternalModifications() {
        ValidationResult internal = new ValidationResult();
        internal.addWarning("A warning");
        assertEquals("The internal result has one message",
                1,
                internal.size());
        assertTrue("The internal result has a warning",
                internal.hasWarnings());
        ValidationResult unmodifiable = ValidationResult.unmodifiableResult(internal);
        assertEquals("The unmodifiable result has one message",
                1,
                unmodifiable.size());
        assertTrue("The unmodifiable result has a warning",
                unmodifiable.hasWarnings());

        internal.addError("An error");
        assertEquals("The internal result has two messages",
                2,
                internal.size());
        assertEquals("The unmodifiable result still has one message",
                1,
                unmodifiable.size());
    }


    public static void testUnmodifiableResultRejectsModifications() {
        testUnmodifiableResultRejectsModifications(ValidationResult.EMPTY);
        testUnmodifiableResultRejectsModifications(ValidationResult.unmodifiableResult(new ValidationResult()));
        ValidationResult result = new ValidationResult();
        result.addWarning("A warning");
        testUnmodifiableResultRejectsModifications(ValidationResult.unmodifiableResult(result));
    }


    private static void testUnmodifiableResultRejectsModifications(ValidationResult result) {
        try {
            result.add(new SimpleValidationMessage("A warning"));
            fail("An unmodifiable result shall reject the #add operation.");
        } catch (UnsupportedOperationException e) {
            // The expected behavior
        }
        try {
            result.addAll(ValidationResults.E1E2E3.getMessages());
            fail("An unmodifiable result shall reject the #addAll operation.");
        } catch (UnsupportedOperationException e) {
            // The expected behavior
        }
        try {
            result.addAllFrom(ValidationResults.E1E2E3);
            fail("An unmodifiable result shall reject the #addAllFrom operation.");
        } catch (UnsupportedOperationException e) {
            // The expected behavior
        }
        try {
            result.addError("An error");
            fail("An unmodifiable result shall reject the #addError operation.");
        } catch (UnsupportedOperationException e) {
            // The expected behavior
        }
        try {
            result.addWarning("A warning");
            fail("An unmodifiable result shall reject the #addWarning operation.");
        } catch (UnsupportedOperationException e) {
            // The expected behavior
        }
    }

}
