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

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import org.junit.Test;

import com.jgoodies.validation.util.ValidationUtils;

/**
 * A test case for class {@link ValidationUtils}.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.13 $
 *
 * @see Calendar
 */
public final class ValidationUtilsTest extends TestCase {

    private static final String EMPTY              = "";
    private static final String BLANK              = "  ";
    private static final String ALPHA              = "abc";
    private static final String ALPHA_SPACE        = "ab c";
    private static final String ALPHA_DASH         = "ab-c";
    private static final String ALPHANUMERIC       = "ab2c";
    private static final String ALPHANUMERIC_SPACE = "ab2 c";
    private static final String ALPHANUMERIC_DASH  = "ab2-c";
    private static final String NUMERIC            = "123";
    private static final String NUMERIC_SPACE      = "12 3";
    private static final String NUMERIC_DASH       = "12-3";
    private static final String NUMERIC_POINT      = "12.3";


    // Character Tests ********************************************************

    @Test
    public static void testIsAlpha() {
        assertFalse("Null is not alpha",                ValidationUtils.isAlpha(null));
        assertTrue ("EMPTY is alpha",                   ValidationUtils.isAlpha(EMPTY));
        assertFalse("BLANK is not alpha",               ValidationUtils.isAlpha(BLANK));
        assertTrue ("ALPHA is alpha",                   ValidationUtils.isAlpha(ALPHA));
        assertFalse("ALPHA_SPACE is not alpha",         ValidationUtils.isAlpha(ALPHA_SPACE));
        assertFalse("ALPHA_DASH is not alpha",          ValidationUtils.isAlpha(ALPHA_DASH));
        assertFalse("ALPHANUMERIC is not alpha",        ValidationUtils.isAlpha(ALPHANUMERIC));
        assertFalse("ALPHANUMERIC_SPACE is not alpha",  ValidationUtils.isAlpha(ALPHANUMERIC_SPACE));
        assertFalse("ALPHANUMERIC_DASH is not alpha",   ValidationUtils.isAlpha(ALPHANUMERIC_DASH));
        assertFalse("NUMERIC is not alpha",             ValidationUtils.isAlpha(NUMERIC));
        assertFalse("NUMERIC_SPACE is not alpha",       ValidationUtils.isAlpha(NUMERIC_SPACE));
        assertFalse("NUMERIC_DASH is not alpha",        ValidationUtils.isAlpha(NUMERIC_DASH));
    }


    @Test
    public static void testIsAlphaSpace() {
        assertFalse("Null is not alpha-space",                ValidationUtils.isAlphaSpace(null));
        assertTrue ("EMPTY is alpha-space",                   ValidationUtils.isAlphaSpace(EMPTY));
        assertTrue ("BLANK is alpha-space",                   ValidationUtils.isAlphaSpace(BLANK));
        assertTrue ("ALPHA is alpha-space",                   ValidationUtils.isAlphaSpace(ALPHA));
        assertTrue ("ALPHA_SPACE is alpha-space",             ValidationUtils.isAlphaSpace(ALPHA_SPACE));
        assertFalse("ALPHA_DASH is not alpha-space",          ValidationUtils.isAlphaSpace(ALPHA_DASH));
        assertFalse("ALPHANUMERIC is not alpha-space",        ValidationUtils.isAlphaSpace(ALPHANUMERIC));
        assertFalse("ALPHANUMERIC_SPACE is not alpha-space",  ValidationUtils.isAlphaSpace(ALPHANUMERIC_SPACE));
        assertFalse("ALPHANUMERIC_DASH is not alpha-space",   ValidationUtils.isAlphaSpace(ALPHANUMERIC_DASH));
        assertFalse("NUMERIC is not alpha-space",             ValidationUtils.isAlphaSpace(NUMERIC));
        assertFalse("NUMERIC_SPACE is not alpha-space",       ValidationUtils.isAlphaSpace(NUMERIC_SPACE));
        assertFalse("NUMERIC_DASH is not alpha-space",        ValidationUtils.isAlphaSpace(NUMERIC_DASH));
    }


    @Test
    public static void testIsAlphanumeric() {
        assertFalse("Null is not alphanumeric",                ValidationUtils.isAlphanumeric(null));
        assertTrue ("EMPTY is alphanumeric",                   ValidationUtils.isAlphanumeric(EMPTY));
        assertFalse("BLANK is not alphanumeric",               ValidationUtils.isAlphanumeric(BLANK));
        assertTrue ("ALPHA is alphanumeric",                   ValidationUtils.isAlphanumeric(ALPHA));
        assertFalse("ALPHA_SPACE is not alphanumeric",         ValidationUtils.isAlphanumeric(ALPHA_SPACE));
        assertFalse("ALPHA_DASH is not alphanumeric",          ValidationUtils.isAlphanumeric(ALPHA_DASH));
        assertTrue ("ALPHANUMERIC is alphanumeric",            ValidationUtils.isAlphanumeric(ALPHANUMERIC));
        assertFalse("ALPHANUMERIC_SPACE is not alphanumeric",  ValidationUtils.isAlphanumeric(ALPHANUMERIC_SPACE));
        assertFalse("ALPHANUMERIC_DASH is not alphanumeric",   ValidationUtils.isAlphanumeric(ALPHANUMERIC_DASH));
        assertTrue ("NUMERIC is alphanumeric",                 ValidationUtils.isAlphanumeric(NUMERIC));
        assertFalse("NUMERIC_SPACE is not alphanumeric",       ValidationUtils.isAlphanumeric(NUMERIC_SPACE));
        assertFalse("NUMERIC_DASH is not alphanumeric",        ValidationUtils.isAlphanumeric(NUMERIC_DASH));
    }


    @Test
    public static void testIsAlphanumericSpace() {
        assertFalse("Null is not alphanumeric-space",                ValidationUtils.isAlphanumericSpace(null));
        assertTrue ("EMPTY is alphanumeric-space",                   ValidationUtils.isAlphanumericSpace(EMPTY));
        assertTrue ("BLANK is alphanumeric-space",                   ValidationUtils.isAlphanumericSpace(BLANK));
        assertTrue ("ALPHA is alphanumeric-space",                   ValidationUtils.isAlphanumericSpace(ALPHA));
        assertTrue ("ALPHA_SPACE is alphanumeric-space",             ValidationUtils.isAlphanumericSpace(ALPHA_SPACE));
        assertFalse("ALPHA_DASH is not alphanumeric-space",          ValidationUtils.isAlphanumericSpace(ALPHA_DASH));
        assertTrue ("ALPHANUMERIC is alphanumeric-space",            ValidationUtils.isAlphanumericSpace(ALPHANUMERIC));
        assertTrue ("ALPHANUMERIC_SPACE is alphanumeric-space",      ValidationUtils.isAlphanumericSpace(ALPHANUMERIC_SPACE));
        assertFalse("ALPHANUMERIC_DASH is not alphanumeric-space",   ValidationUtils.isAlphanumericSpace(ALPHANUMERIC_DASH));
        assertTrue ("NUMERIC is alphanumeric-space",                 ValidationUtils.isAlphanumericSpace(NUMERIC));
        assertTrue ("NUMERIC_SPACE is not alphanumeric-space",       ValidationUtils.isAlphanumericSpace(NUMERIC_SPACE));
        assertFalse("NUMERIC_DASH is not alphanumeric-space",        ValidationUtils.isAlphanumericSpace(NUMERIC_DASH));
    }


    @Test
    public static void testIsNumeric() {
        assertFalse("Null is not numeric",                ValidationUtils.isNumeric(null));
        assertTrue ("EMPTY is numeric",                   ValidationUtils.isNumeric(EMPTY));
        assertFalse("BLANK is not numeric",               ValidationUtils.isNumeric(BLANK));
        assertFalse("ALPHA is not numeric",               ValidationUtils.isNumeric(ALPHA));
        assertFalse("ALPHA_SPACE is not numeric",         ValidationUtils.isNumeric(ALPHA_SPACE));
        assertFalse("ALPHA_DASH is not numeric",          ValidationUtils.isNumeric(ALPHA_DASH));
        assertFalse("ALPHANUMERIC is not numeric",        ValidationUtils.isNumeric(ALPHANUMERIC));
        assertFalse("ALPHANUMERIC_SPACE is not numeric",  ValidationUtils.isNumeric(ALPHANUMERIC_SPACE));
        assertFalse("ALPHANUMERIC_DASH is not numeric",   ValidationUtils.isNumeric(ALPHANUMERIC_DASH));
        assertTrue ("NUMERIC is numeric",                 ValidationUtils.isNumeric(NUMERIC));
        assertFalse("NUMERIC_SPACE is not numeric",       ValidationUtils.isNumeric(NUMERIC_SPACE));
        assertFalse("NUMERIC_DASH is not numeric",        ValidationUtils.isNumeric(NUMERIC_DASH));
        assertFalse("NUMERIC_POINT is not numeric",       ValidationUtils.isNumeric(NUMERIC_POINT));
    }


    @Test
    public static void testIsNumericSpace() {
        assertFalse("Null is not numeric-space",                ValidationUtils.isNumericSpace(null));
        assertTrue ("EMPTY is numeric-space",                   ValidationUtils.isNumericSpace(EMPTY));
        assertTrue ("BLANK is numeric-space",                   ValidationUtils.isNumericSpace(BLANK));
        assertFalse("ALPHA is not numeric-space",               ValidationUtils.isNumericSpace(ALPHA));
        assertFalse("ALPHA_SPACE is not numeric-space",         ValidationUtils.isNumericSpace(ALPHA_SPACE));
        assertFalse("ALPHA_DASH is not numeric-space",          ValidationUtils.isNumericSpace(ALPHA_DASH));
        assertFalse("ALPHANUMERIC is not numeric-space",        ValidationUtils.isNumericSpace(ALPHANUMERIC));
        assertFalse("ALPHANUMERIC_SPACE is not numeric-space",  ValidationUtils.isNumericSpace(ALPHANUMERIC_SPACE));
        assertFalse("ALPHANUMERIC_DASH is not numeric-space",   ValidationUtils.isNumericSpace(ALPHANUMERIC_DASH));
        assertTrue ("NUMERIC is numeric-space",                 ValidationUtils.isNumericSpace(NUMERIC));
        assertTrue ("NUMERIC_SPACE is numeric-space",           ValidationUtils.isNumericSpace(NUMERIC_SPACE));
        assertFalse("NUMERIC_DASH is not numeric-space",        ValidationUtils.isNumericSpace(NUMERIC_DASH));
        assertFalse("NUMERIC_POINT is not numeric-space",       ValidationUtils.isNumericSpace(NUMERIC_POINT));
    }


    // Calendar Tests *********************************************************

    /**
     * Verifies that {@code #getRelativeCalendar(Calendar, int)}
     * computes proper relative dates within the same month.
     */
    public static void testRelativeCalendarSameMonth() {
        Calendar birthday = new GregorianCalendar(1967, 11,  5); // Dec-05-67
        Calendar nikolaus = new GregorianCalendar(1967, 11,  6); // Dec-06-67
        Calendar xmas1967 = new GregorianCalendar(1967, 11, 24); // Dec-24-67

        assertEquals("Birthday + 0 is birthday.",
                birthday,
                ValidationUtils.getRelativeCalendar(birthday, 0));

        assertEquals("Nikolaus is a day after my birthday.",
                nikolaus,
                ValidationUtils.getRelativeCalendar(birthday, 1));

        assertEquals("My birthday is a day before Nikolaus.",
                birthday,
                ValidationUtils.getRelativeCalendar(nikolaus, -1));

        assertEquals("Christmas is 19 days after my birthday.",
                xmas1967,
                ValidationUtils.getRelativeCalendar(birthday, 19));

        assertEquals("My birthday is 19 days before Christmas 1967.",
                birthday,
                ValidationUtils.getRelativeCalendar(xmas1967, -19));
    }


    /**
     * Verifies that {@code #getRelativeCalendar(Calendar, int)}
     * computes proper relative dates when the month changes.
     */
    public static void testRelativeCalendarDifferentMonths() {
        Calendar johnsBirthday = new GregorianCalendar(2003, 10, 03); // Nov-03-04
        Calendar christmas2003 = new GregorianCalendar(2003, 11, 24); // Dec-24-04

        assertEquals("Christmas 2003 is 51 days after John's birthday.",
                christmas2003,
                ValidationUtils.getRelativeCalendar(johnsBirthday, 51));

        assertEquals("John's birthday is 51 days before Christmas 2003.",
                johnsBirthday,
                ValidationUtils.getRelativeCalendar(christmas2003, -51));
    }


    /**
     * Verifies that {@code #getRelativeCalendar(Calendar, int)}
     * computes proper relative dates when the month changes.
     */
    public static void testRelativeCalendarLeapYear() {
        Calendar feb28th2004 = new GregorianCalendar(2004, 1, 28); // Feb-28-04
        Calendar feb29th2004 = new GregorianCalendar(2004, 1, 29); // Feb-29-04
        Calendar mar1st2004  = new GregorianCalendar(2004, 2, 01); // Mar-01-04

        assertEquals("Feb-29-04 is a day after Feb-28-04.",
                feb29th2004,
                ValidationUtils.getRelativeCalendar(feb28th2004, 1));

        assertEquals("Feb-28-04 is a day before Feb-29-04.",
                feb28th2004,
                ValidationUtils.getRelativeCalendar(feb29th2004, -1));

        assertEquals("Mar-01-04 is a day after Feb-29-04.",
                mar1st2004,
                ValidationUtils.getRelativeCalendar(feb29th2004, 1));

        assertEquals("Feb-29-04 is a day before Mar-01-04.",
                feb29th2004,
                ValidationUtils.getRelativeCalendar(mar1st2004, -1));

        assertEquals("Mar-01-04 is two days after Feb-28-04.",
                mar1st2004,
                ValidationUtils.getRelativeCalendar(feb28th2004, 2));

        assertEquals("Feb-28-04 is two days before Mar-01-04.",
                feb28th2004,
                ValidationUtils.getRelativeCalendar(mar1st2004, -2));
    }



}
