/*
 * Copyright (c) 2002-2011 JGoodies Karsten Lentzsch. All Rights Reserved.
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

package com.jgoodies.binding.tests;

import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import junit.framework.TestCase;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.binder.BasicBinders;
import com.jgoodies.binding.binder.BeanBinder;
import com.jgoodies.binding.binder.ObjectBinder;
import com.jgoodies.binding.binder.PresentationModelBinder;
import com.jgoodies.binding.tests.beans.TestBean;
import com.jgoodies.binding.value.ComponentValueModel;

/**
 * A test case for the binder mechanism that involves classes such as
 * {@link ObjectBinder}, {@link BeanBinder} and {@link PresentationModelBinder}.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.18 $
 */
public final class BinderTest extends TestCase {


    public static void testValueBinding() {
        TestBean bean = new TestBean();
        bean.setReadWriteBooleanProperty(false);
        JCheckBox checkBox = new JCheckBox("Check", true);

        assertFalse("Bean (initial)", bean.isReadWriteBooleanProperty());
        assertTrue("Check box (initial)", checkBox.isSelected());

        BeanBinder binder = BasicBinders.binderFor(bean);
        binder.bindProperty("readWriteBooleanProperty").to(checkBox);

        assertFalse("Bean (bound)", bean.isReadWriteBooleanProperty());
        assertFalse("Check box (bound)", checkBox.isSelected());

        bean.setReadWriteBooleanProperty(true);
        assertTrue("Bean (bean update)", bean.isReadWriteBooleanProperty());
        assertTrue("Check box (bean update)", checkBox.isSelected());

        checkBox.setSelected(false);
        assertFalse("Bean (check box update)", bean.isReadWriteBooleanProperty());
        assertFalse("Check box (box update)", checkBox.isSelected());
    }


    public static void testComponentPropertyBinding() {
        final String propertyName = "readWriteBooleanProperty";
        TestBean bean = new TestBean();
        PresentationModel<TestBean> presentationModel =
                new PresentationModel<TestBean>(bean);
        ComponentValueModel componentModel =
                presentationModel.getComponentModel(propertyName);
        JCheckBox checkBox = new JCheckBox("Check", true);

        assertTrue("Model enabled (initial)", componentModel.isEnabled());
        assertTrue("Check box enabled (initial)", checkBox.isEnabled());

        componentModel.setEnabled(false);
        assertFalse("Model enabled (setup)", componentModel.isEnabled());
        assertTrue("Check box enabled (setup)", checkBox.isEnabled());

        PresentationModelBinder binder = BasicBinders.binderFor(presentationModel);
        binder.bindBeanProperty(propertyName).to(checkBox);

        // Binding synchronizes the component property with model state
        assertFalse("Model enabled (bound)", componentModel.isEnabled());
        assertFalse("Check box enabled (bound)", checkBox.isEnabled());

        // ComponentValueModel changes update the component property
        componentModel.setEnabled(true);
        assertTrue("Model enabled (update)", componentModel.isEnabled());
        assertTrue("Check box enabled (update)", checkBox.isEnabled());

        // Component changes do not update the model property
        checkBox.setEnabled(false);
        assertTrue("Model enabled (box update)", componentModel.isEnabled());
        assertFalse("Check box enabled (box update)", checkBox.isEnabled());
    }


    public static void testFormattedRetainsComponentPropertyBinding() {
        final String propertyName = "readWriteIntProperty";
        TestBean bean = new TestBean();
        PresentationModel<TestBean> presentationModel =
                new PresentationModel<TestBean>(bean);
        ComponentValueModel componentModel =
                presentationModel.getComponentModel(propertyName);
        JTextField textField = new JTextField();

        componentModel.setEnabled(false);
        assertFalse("Model enabled (setup)", componentModel.isEnabled());
        assertTrue("Field enabled (setup)", textField.isEnabled());

        PresentationModelBinder binder = BasicBinders.binderFor(presentationModel);
        binder.bindBeanProperty(propertyName)
            .formatted(NumberFormat.getIntegerInstance())
            .to(textField);

        // Binding synchronizes the component property with model state
        assertFalse("Model enabled (bound)", componentModel.isEnabled());
        assertFalse("Field enabled (bound)", textField.isEnabled());

        // ComponentValueModel changes update the component property
        componentModel.setEnabled(true);
        assertTrue("Model enabled (update)", componentModel.isEnabled());
        assertTrue("Field enabled (update)", textField.isEnabled());

        // Component changes do not update the model property
        textField.setEnabled(false);
        assertTrue("Model enabled (box update)", componentModel.isEnabled());
        assertFalse("Field enabled (box update)", textField.isEnabled());
    }


    public static void testComboBoxValidationMessageKey() {
        final String propertyName = "readWriteObjectProperty";
        TestBean bean = new TestBean();
        PresentationModel<TestBean> presentationModel =
                new PresentationModel<TestBean>(bean);
        JComboBox comboBox = new JComboBox();

        PresentationModelBinder binder = BasicBinders.binderFor(presentationModel);
        binder.bindBeanProperty(propertyName)
            .asSelectionIn(new String[]{"One", "Two", "Three"})
            .to(comboBox);

        Object[] expected = new Object[]{propertyName};
        Object[] actual = (Object[]) comboBox.getClientProperty("validation.messageKeys");
        assertTrue("Combo box validation message keys",
                Arrays.equals(expected, actual));
    }


}
