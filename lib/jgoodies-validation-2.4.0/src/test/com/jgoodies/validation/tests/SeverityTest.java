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

/**
 * A test case for class {@link Severity}.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.10 $
 */
@SuppressWarnings("static-method")
public final class SeverityTest extends TestCase {

    /**
     * Verifies that the computed and expected maximum Severity are the same.
     */
    public void testMax() {
        testMax(Severity.ERROR,   Severity.ERROR,   Severity.ERROR);
        testMax(Severity.ERROR,   Severity.WARNING, Severity.ERROR);
        testMax(Severity.ERROR,   Severity.INFO,    Severity.ERROR);
        testMax(Severity.ERROR,   Severity.OK,      Severity.ERROR);
        testMax(Severity.WARNING, Severity.ERROR,   Severity.ERROR);
        testMax(Severity.WARNING, Severity.WARNING, Severity.WARNING);
        testMax(Severity.WARNING, Severity.INFO,    Severity.WARNING);
        testMax(Severity.WARNING, Severity.OK,      Severity.WARNING);
        testMax(Severity.INFO,    Severity.ERROR,   Severity.ERROR);
        testMax(Severity.INFO,    Severity.WARNING, Severity.WARNING);
        testMax(Severity.INFO,    Severity.INFO,    Severity.INFO);
        testMax(Severity.INFO,    Severity.OK,      Severity.INFO);
        testMax(Severity.OK,      Severity.ERROR,   Severity.ERROR);
        testMax(Severity.OK,      Severity.WARNING, Severity.WARNING);
        testMax(Severity.OK,      Severity.INFO,    Severity.INFO);
        testMax(Severity.OK,      Severity.OK,      Severity.OK);
    }

    /**
     * Checks for valid comparisons.
     */
    public void testCompareTo() {
        testCompareTo(Severity.ERROR,   Severity.ERROR,    0);
        testCompareTo(Severity.ERROR,   Severity.WARNING, -1);
        testCompareTo(Severity.ERROR,   Severity.INFO,    -1);
        testCompareTo(Severity.ERROR,   Severity.OK,      -1);
        testCompareTo(Severity.WARNING, Severity.ERROR,    1);
        testCompareTo(Severity.WARNING, Severity.WARNING,  0);
        testCompareTo(Severity.WARNING, Severity.INFO,    -1);
        testCompareTo(Severity.WARNING, Severity.OK,      -1);
        testCompareTo(Severity.INFO,    Severity.ERROR,    1);
        testCompareTo(Severity.INFO,    Severity.WARNING,  1);
        testCompareTo(Severity.INFO,    Severity.INFO,     0);
        testCompareTo(Severity.INFO,    Severity.OK,      -1);
        testCompareTo(Severity.OK,      Severity.ERROR,    1);
        testCompareTo(Severity.OK,      Severity.WARNING,  1);
        testCompareTo(Severity.OK,      Severity.INFO,     1);
        testCompareTo(Severity.OK,      Severity.OK,       0);
    }


    // Helper Code ************************************************************

    private static void testMax(Severity s1, Severity s2, Severity max) {
        Severity computedMax = Severity.max(s1, s2);
        assertSame("The max(" + s1 + ", " + s2 + ")", max, computedMax);
    }

    private static void testCompareTo(Severity s1, Severity s2, int sgn) {
        int computedCompared = s1.compareTo(s2);
        int computedSgn = computedCompared == 0
            ? 0
            : Math.abs(computedCompared) / computedCompared;
        assertEquals(s1.toString() + ".compareTo(" + s2 + ")", sgn, computedSgn);
    }

}
