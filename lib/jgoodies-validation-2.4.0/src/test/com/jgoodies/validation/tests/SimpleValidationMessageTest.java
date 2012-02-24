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

import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.message.SimpleValidationMessage;

/**
 * A test case for class {@link SimpleValidationMessage}.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.8 $
 */
public final class SimpleValidationMessageTest extends TestCase {

    private ValidationMessage m1a;
    private ValidationMessage m1b;
    private ValidationMessage m2;


    @Override
    protected void setUp() {
        m1a = new SimpleValidationMessage("Message1");
        m1b = new SimpleValidationMessage("Message1");
        m2  = new SimpleValidationMessage("Message2");
    }


    @Override
    protected void tearDown() {
        m1a = null;
        m1b = null;
        m2 = null;
    }


    /**
     * Some tests for the #equals implementation.
     */
    public void testEquals() {
        assertEquals("m1a.equals(m1a).", m1a, m1a);
        assertEquals("m1a.equals(m1b).", m1a, m1b);
        assertEquals("m1b.equals(m1a).", m1b, m1a);

        assertFalse("!m1a.equals(\"hello\")", m1a.equals("hello"));
        assertFalse("!m1a.equals(m2)", m1a.equals(m2));
    }


    /**
     * Some tests for the #hashCode implementation.
     */
    public void testHashCode() {
        assertTrue("m1a.hashCode == m2a.hashCode()",
                m1a.hashCode() == m1b.hashCode());
        assertTrue("m1a.hashCode != m2.hashCode()",
                m1a.hashCode() != m2.hashCode());
    }


}
