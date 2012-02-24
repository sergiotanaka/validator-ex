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

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;

import junit.framework.TestCase;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.tests.event.ListDataReport;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationResultListAdapter;

/**
 * A test case for class {@link ValidationResultListAdapter}.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.7 $
 */
@SuppressWarnings("static-method")
public final class ValidationResultListAdapterTest extends TestCase {

    /**
     * Verifies that the source of the ListDataEvents fired
     * is the adapter, not its internal PropertyChangeListener.
     */
    public void testAdapterIsEventSource() {
        ValidationResultModel model = new DefaultValidationResultModel();
        ListModel listModel = new ValidationResultListAdapter(model);
        ListDataReport report = new ListDataReport();
        listModel.addListDataListener(report);

        model.setResult(ValidationResults.E1W1);

        assertTrue("The result model fired an event.",
                report.eventCount() > 0);

        assertSame("The event source is the ValidationResultListAdapter.",
                listModel,
                report.lastEvent().getSource());
    }


    // List Change Events *****************************************************

    /**
     * Tests the ListDataEvents fired during list changes.
     * The transistions are {} -> {} -> {a, b} -> {b, c} -> {a, b, c} -> {}.
     */
    public void testListChangeEvents() {
        ValidationResult result1 = ValidationResult.EMPTY;
        ValidationResult result2 = ValidationResult.EMPTY;
        ValidationResult result3 = ValidationResults.E1W1;
        ValidationResult result4 = ValidationResults.E1W2;
        ValidationResult result5 = ValidationResults.E1E1W1;
        ValidationResult result6 = ValidationResult.EMPTY;

        ValidationResultModel model = new DefaultValidationResultModel();
        model.setResult(result1);
        ListModel listModel = new ValidationResultListAdapter(model);
        ListDataReport report = new ListDataReport();
        listModel.addListDataListener(report);

        model.setResult(result2);
        assertEquals("The transistion {} -> {} fires no ListDataEvent.",
                0,
                report.eventCount());

        report.clearEventList();
        model.setResult(result3);
        assertEquals("The transistion {} -> {a, b} fires 1 add event.",
                1,
                report.eventCount());
        assertEvent("The transistion {} -> {a, b} fires an add event with interval[0, 1].",
                ListDataEvent.INTERVAL_ADDED, 0, 1,
                report.lastEvent());

        report.clearEventList();
        model.setResult(result4);
        assertEquals("The transistion {a, b} -> {b, c} fires 1 add event.",
                1,
                report.eventCount());
        assertEvent("The transistion {a, b} -> {b, c} fires an add event with interval[0, 1].",
                ListDataEvent.CONTENTS_CHANGED, 0, 1,
                report.lastEvent());

        report.clearEventList();
        model.setResult(result5);
        assertEquals("The transistion {a, b} -> {b, c, d} fires two events.",
                2,
                report.eventCount());
        assertEvent("The transistion {a, b} -> {b, c, d} fires a remove event with interval[0, 1].",
                ListDataEvent.INTERVAL_REMOVED, 0, 1,
                report.previousEvent());
        assertEvent("The transistion {a, b} -> {b, c, d} fires an add event with interval[0, 2].",
                ListDataEvent.INTERVAL_ADDED, 0, 2,
                report.lastEvent());

        report.clearEventList();
        model.setResult(result6);
        assertEquals("The transistion {b, c, d} -> {} fires one event.",
                1,
                report.eventCount());
        assertEvent("The transistion {b, c, d} -> {} fires a remove event with interval[0, 1].",
                ListDataEvent.INTERVAL_REMOVED, 0, 2,
                report.lastEvent());
    }


    private static void assertEvent(String description, int eventType, int index0, int index1, ListDataEvent event) {
        assertEquals("Type: " + description,
                eventType,
                event.getType());
        assertEquals("Index0: " + description,
                index0,
                event.getIndex0());
        assertEquals("Index1: " + description,
                index1,
                event.getIndex1());
    }



}
