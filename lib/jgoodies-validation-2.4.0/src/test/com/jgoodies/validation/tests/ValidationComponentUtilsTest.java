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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

import junit.framework.TestCase;

import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * A test case for class {@link ValidationComponentUtils}.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.10 $
 */
public final class ValidationComponentUtilsTest extends TestCase {

    private JTextComponent blankField;
    private JTextComponent filledField;
    private JTextComponent blankMandatoryField;
    private JTextComponent filledMandatoryField;
    private JTextComponent disabledField;
    private JTextComponent nonEditableField;
    private JTextComponent customField;
    private Container container;


    // Initialization *********************************************************

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setupComponents();
        setupComponentAnnotations();

        container = new JPanel();
        container.add(blankField);
        container.add(filledField);
        container.add(blankMandatoryField);
        container.add(filledMandatoryField);
        container.add(disabledField);
        container.add(nonEditableField);
        container.add(customField);
    }


    private void setupComponents() {
        blankField           = new JTextField("  ");
        filledField          = new JTextField("Filled");
        blankMandatoryField  = new JTextField("  ");
        filledMandatoryField = new JTextField("Filled");
        disabledField        = new JTextField("disabled");
        disabledField.setEnabled(false);
        nonEditableField     = new JTextField("non-editable");
        nonEditableField.setEditable(false);
        customField          = new JTextField("custom");
        customField.setBackground(new Color(5, 12, 67));
    }


    private void setupComponentAnnotations() {
        ValidationComponentUtils.setMandatory(blankMandatoryField, true);
        ValidationComponentUtils.setMandatory(filledMandatoryField, true);
    }


    @Override
    protected void tearDown() throws Exception {
        super.setUp();
        blankField  = null;
        filledField = null;
        blankMandatoryField  = null;
        filledMandatoryField = null;
        disabledField = null;
        nonEditableField = null;
        container = null;
    }


    // Tests *****************************************************************

    public void testUpdateComponentTreeMandatoryBackground() {
        Color defaultBackground     = getDefaultBackground();
        Color mandatoryBackground   = ValidationComponentUtils.getMandatoryBackground();
        Color disabledBackground    = disabledField.getBackground();
        Color nonEditableBackground = nonEditableField.getBackground();

        ValidationComponentUtils.updateComponentTreeMandatoryBackground(
                container);

        assertBackground("Blank field has the default background",
                blankField,
                defaultBackground);

        assertBackground("Filled field has the default background",
                filledField,
                defaultBackground);

        assertBackground("Mandatory blank field has the mandatory background.",
                blankMandatoryField,
                mandatoryBackground);

        assertBackground("Mandatory filled field has the mandatory background.",
                filledMandatoryField,
                mandatoryBackground);

        assertBackground("Mandatory filled field has the mandatory background.",
                filledMandatoryField,
                mandatoryBackground);

        assertBackground("Disabled field has the disabled background.",
                disabledField,
                disabledBackground);

        assertBackground("Non-editable field has the non-editable background.",
                nonEditableField,
                nonEditableBackground);
    }


    public void testUpdateComponentTreeMandatoryAndBlankBackground() {
        Color defaultBackground     = getDefaultBackground();
        Color mandatoryBackground   = ValidationComponentUtils.getMandatoryBackground();
        Color disabledBackground    = disabledField.getBackground();
        Color nonEditableBackground = nonEditableField.getBackground();

        ValidationComponentUtils.updateComponentTreeMandatoryAndBlankBackground(
                container);

        assertBackground("Blank field has the default background",
                blankField,
                defaultBackground);

        assertBackground("Filled field has the default background",
                filledField,
                defaultBackground);

        assertBackground("Mandatory blank field has the mandatory background.",
                blankMandatoryField,
                mandatoryBackground);

        assertBackground("Mandatory filled field has the default background.",
                filledMandatoryField,
                defaultBackground);

        assertBackground("Disabled field has the disabled background.",
                disabledField,
                disabledBackground);

        assertBackground("Non-editable field has the non-editable background.",
                nonEditableField,
                nonEditableBackground);
    }


    public void testUpdateComponentTreeMandatoryBorder() {
        Border defaultBorder     = getDefaultBorder();
        Border mandatoryBorder   = ValidationComponentUtils.getMandatoryBorder();
        Border disabledBorder    = disabledField.getBorder();
        Border nonEditableBorder = nonEditableField.getBorder();

        ValidationComponentUtils.updateComponentTreeMandatoryBorder(
                container);

        assertBorder("Blank field has the default border",
                blankField,
                defaultBorder);

        assertBorder("Filled field has the default border",
                filledField,
                defaultBorder);

        assertBorder("Mandatory blank field has the mandatory border.",
                blankMandatoryField,
                mandatoryBorder);

        assertBorder("Mandatory filled field has the mandatory border.",
                filledMandatoryField,
                mandatoryBorder);

        assertBorder("Disabled field has the disabled border.",
                disabledField,
                disabledBorder);

        assertBorder("Non-editable field has the non-editable border.",
                nonEditableField,
                nonEditableBorder);
    }


    public void testUpdateComponentTreeSeverityBackground() {
        Color defaultBackground     = getDefaultBackground();
        Color warningBackground     = ValidationComponentUtils.getWarningBackground();
        Color errorBackground       = ValidationComponentUtils.getErrorBackground();
        Color disabledBackground    = disabledField.getBackground();
        Color nonEditableBackground = nonEditableField.getBackground();
        Color customBackground      = customField.getBackground();

        testUpdateComponentTreeSeverityBackground(
                "A field without message key has the default background for an empty result.",
                defaultBackground,
                disabledBackground,
                nonEditableBackground,
                customBackground,
                null, ValidationResult.EMPTY);
        testUpdateComponentTreeSeverityBackground(
                "A field without message key has the default background for a warning result.",
                defaultBackground,
                disabledBackground,
                nonEditableBackground,
                customBackground,
                null, ValidationResults.W1);
        testUpdateComponentTreeSeverityBackground(
                "A field without message key has the default background for an error result.",
                defaultBackground,
                disabledBackground,
                nonEditableBackground,
                customBackground,
                null, ValidationResults.E1);


        // If no message is associated, show the default background.
        testUpdateComponentTreeSeverityBackground(
                "A field with message key has the default background for an empty result.",
                defaultBackground,
                disabledBackground,
                nonEditableBackground,
                customBackground,
                ValidationResults.KEY1, ValidationResult.EMPTY);
        testUpdateComponentTreeSeverityBackground(
                "A field with message key has the warning background if the warning result contains this message.",
                warningBackground,
                warningBackground,
                warningBackground,
                warningBackground,
                ValidationResults.KEY1, ValidationResults.W1);
        testUpdateComponentTreeSeverityBackground(
                "A field with message key has the default background if the warning result doesn't contain this message.",
                defaultBackground,
                disabledBackground,
                nonEditableBackground,
                customBackground,
                ValidationResults.KEY1, ValidationResults.W2);
        testUpdateComponentTreeSeverityBackground(
                "A field with message key has the error background if the error result contains this message.",
                errorBackground,
                errorBackground,
                errorBackground,
                errorBackground,
                ValidationResults.KEY1, ValidationResults.E1);
        testUpdateComponentTreeSeverityBackground(
                "A field with message key has the default background if the error result doesn't contain this message.",
                defaultBackground,
                disabledBackground,
                nonEditableBackground,
                customBackground,
                ValidationResults.KEY1, ValidationResults.E2);
        testUpdateComponentTreeSeverityBackground(
                "A field with message key has the default background for an empty result.",
                defaultBackground,
                disabledBackground,
                nonEditableBackground,
                customBackground,
                ValidationResults.KEY1, ValidationResult.EMPTY);
    }


    private void testUpdateComponentTreeSeverityBackground(
            String assertionText,
            Color expectedBackground,
            Color disabledBackground,
            Color nonEditableBackground,
            Color customBackground,
            Object messageKey, ValidationResult validationResult) {
        // Set the message keys to all test components.
        ValidationComponentUtils.setMessageKey(blankField, messageKey);
        ValidationComponentUtils.setMessageKey(filledField, messageKey);
        ValidationComponentUtils.setMessageKey(blankMandatoryField, messageKey);
        ValidationComponentUtils.setMessageKey(filledMandatoryField, messageKey);
        ValidationComponentUtils.setMessageKey(disabledField, messageKey);
        ValidationComponentUtils.setMessageKey(nonEditableField, messageKey);
        ValidationComponentUtils.setMessageKey(customField, messageKey);

        ValidationComponentUtils.updateComponentTreeSeverityBackground(
                container,
                validationResult);

        assertBackground("Blank field: " + assertionText,
                    blankField,
                    expectedBackground);
        assertBackground("Filled field: " + assertionText,
                filledField,
                expectedBackground);
        assertBackground("Blank mandatory field: " + assertionText,
                blankMandatoryField,
                expectedBackground);
        assertBackground("Filled mandatory field: " + assertionText,
                filledMandatoryField,
                expectedBackground);
        assertBackground("Disabled field: " + assertionText,
                disabledField,
                disabledBackground);
        assertBackground("Non-editable field: " + assertionText,
                nonEditableField,
                nonEditableBackground);
        assertBackground("Field with custom background: " + assertionText,
                customField,
                customBackground);
    }


    public void testUpdateComponentTreeSeverity() {
        testUpdateComponentTreeSeverity(
                "A field without message key has no severity for an empty result.",
                null,
                ValidationResult.EMPTY,
                null);
        testUpdateComponentTreeSeverity(
                "A field without message key has no severity for a warning result.",
                null,
                ValidationResults.W1,
                null);
        testUpdateComponentTreeSeverity(
                "A field without message key has no severity for an error result.",
                null,
                ValidationResults.E1,
                null);


        testUpdateComponentTreeSeverity(
                "A field with message key has OK severity for an empty result.",
                ValidationResults.KEY1,
                ValidationResult.EMPTY,
                Severity.OK);
        testUpdateComponentTreeSeverity(
                "A field with message key has the warning severity if the warning result contains this message.",
                ValidationResults.KEY1,
                ValidationResults.W1,
                Severity.WARNING);
        testUpdateComponentTreeSeverity(
                "A field with message key has the OK severity if the warning result doesn't contain this message.",
                ValidationResults.KEY1,
                ValidationResults.W2,
                Severity.OK);
        testUpdateComponentTreeSeverity(
                "A field with message has the error severity if the error result contains this message.",
                ValidationResults.KEY1,
                ValidationResults.E1,
                Severity.ERROR);
        testUpdateComponentTreeSeverity(
                "A field with message key has the OK severity if the error result doesn't contain this message.",
                ValidationResults.KEY1,
                ValidationResults.E2,
                Severity.OK);
        testUpdateComponentTreeSeverity(
                "A field with message key has the OK severity for an empty result.",
                ValidationResults.KEY1,
                ValidationResult.EMPTY,
                Severity.OK);
        testUpdateComponentTreeSeverity(
                "Second check: A field without message key has no severity for an empty result.",
                null,
                ValidationResult.EMPTY,
                null);
    }


    private void testUpdateComponentTreeSeverity(
            String assertionText,
            Object messageKey,
            ValidationResult validationResult,
            Severity expectedSeverity) {
        ValidationComponentUtils.setMessageKey(blankField, messageKey);
        ValidationComponentUtils.updateComponentTreeSeverity(
                container,
                validationResult);
        assertEquals(assertionText,
                expectedSeverity,
                ValidationComponentUtils.getSeverity(blankField));
    }


    // Helper Code ************************************************************

    private static void assertBackground(String text, Component component, Color background) {
        assertEquals(text, background, component.getBackground());
    }


    private static void assertBorder(String text, JComponent component, Border border) {
        assertEquals(text, border, component.getBorder());
    }


    private static Color getDefaultBackground() {
        return new JTextField().getBackground();
    }


    private static Border getDefaultBorder() {
        return new JTextField().getBorder();
    }

}
