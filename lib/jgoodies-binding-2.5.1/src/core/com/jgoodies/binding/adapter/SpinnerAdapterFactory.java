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

package com.jgoodies.binding.adapter;

import static com.jgoodies.common.base.Preconditions.checkNotNull;

import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.jgoodies.binding.value.ValueModel;

/**
 * A factory that vends {@link SpinnerModel} implementations that are bound to
 * a ValueModel. Can be used to bind a ValueModel to instances of JSpinner.<p>
 *
 * To keep the ValueModel and SpinnerModel synchronized, this class listens
 * to changes in both sides and updates the other silently, i.e. without
 * firing a duplicate change event.<p>
 *
 * <strong>Constraints:</strong>
 * The ValueModel's type must be compatible with the type required by the
 * referenced SpinnerModel. For example a {@link SpinnerNumberModel} requires
 * {@code Number} values.
 *
 * <strong>Example:</strong><pre>
 * // General Connection
 * ValueModel   levelModel   = new PropertyAdapter(settings, "level", true);
 * SpinnerModel spinnerModel = new SpinnerNumberModel(9, 5, 10, 1);
 * Object defaultValue       = new Integer(9);
 * SpinnerAdapterFactory.connect(spinnerModel, levelModel, defaultValue);
 * JSpinner levelSpinner = new JSpinner(spinnerModel);
 *
 * // Short Form
 * ValueModel levelModel = new PropertyAdapter(settings, "level", true);
 * SpinnerNumberModel spinnerModel =
 *     SpinnerAdapterFactory.createNumberAdapter(levelModel, 5, 10, 1);
 * JSpinner levelSpinner = new JSpinner(spinnerModel);
 * </pre>
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.17 $
 *
 * @see     ValueModel
 * @see     SpinnerModel
 * @see     javax.swing.JSpinner
 *
 * @since 1.1
 */
public final class SpinnerAdapterFactory {

    private SpinnerAdapterFactory() {
    // Override default constructor; prevents instantiation.
    }


    // Factory Methods ********************************************************

    /**
     * Creates and returns a {@code SpinnerDateModel} bound to the given
     * {@code valueModel}. The {@code calendarField}
     * is equal to {@code Calendar.DAY_OF_MONTH}; there are no
     * {@code start}/{@code end} limits.
     *
     * @param valueModel   a {@code Date} typed model that holds the spinner value
     * @param defaultDate  the date used if the valueModel's value is {@code null}
     * @return a {@code SpinnerDateModel} bound to the given
     *     {@code valueModel} without start and end limits using
     *     {@code Calendar.DAY_OF_MONTH} as calendar field
     *
     * @throws NullPointerException       if the valueModel or defaultDate is {@code null}
     */
    public static SpinnerDateModel createDateAdapter(ValueModel valueModel, Date defaultDate) {
        return createDateAdapter(valueModel, defaultDate, null, null, Calendar.DAY_OF_MONTH);
    }


    /**
     * Creates and returns a {@code SpinnerDateModel} that represents a sequence
     * of dates and is bound to the given {@code valueModel}.
     * The dates are between {@code start} and {@code end}.  The
     * {@code nextValue} and {@code previousValue} methods
     * compute elements of the sequence by advancing or reversing
     * the current date {@code value} by the
     * {@code calendarField} time unit.  For a precise description
     * of what it means to increment or decrement a {@code Calendar}
     * {@code field}, see the {@code add} method in
     * {@code java.util.Calendar}.<p>
     *
     * The {@code start} and {@code end} parameters can be
     * {@code null} to indicate that the range doesn't have an
     * upper or lower bound.  If {@code value} or
     * {@code calendarField} is {@code null}, or if both
     * {@code start} and {@code end} are specified and
     * {@code minimum &gt; maximum} then an
     * {@code IllegalArgumentException} is thrown.
     * Similarly if {@code (minimum &lt;= value &lt;= maximum)} is false,
     * an IllegalArgumentException is thrown.<p>
     *
     * <strong>This method has not been tested.</strong>
     *
     * @param valueModel   a {@code Date} typed model that holds the spinner value
     * @param defaultDate  the date used if the valueModel's value is {@code null}
     * @param start the first date in the sequence or {@code null}
     * @param end the last date in the sequence or {@code null}
     * @param calendarField one of
     *   <ul>
     *    <li>{@code Calendar.ERA}
     *    <li>{@code Calendar.YEAR}
     *    <li>{@code Calendar.MONTH}
     *    <li>{@code Calendar.WEEK_OF_YEAR}
     *    <li>{@code Calendar.WEEK_OF_MONTH}
     *    <li>{@code Calendar.DAY_OF_MONTH}
     *    <li>{@code Calendar.DAY_OF_YEAR}
     *    <li>{@code Calendar.DAY_OF_WEEK}
     *    <li>{@code Calendar.DAY_OF_WEEK_IN_MONTH}
     *    <li>{@code Calendar.AM_PM}
     *    <li>{@code Calendar.HOUR}
     *    <li>{@code Calendar.HOUR_OF_DAY}
     *    <li>{@code Calendar.MINUTE}
     *    <li>{@code Calendar.SECOND}
     *    <li>{@code Calendar.MILLISECOND}
     *   </ul>
     * @return a {@code SpinnerDateModel} bound to the given
     *     {@code valueModel} using the specified start and end dates
     *     and calendar field.
     *
     * @throws NullPointerException       if the valueModel or defaultDate is {@code null}
     * @throws IllegalArgumentException   if {@code calendarField} isn't valid,
     *    or if the following expression is
     *    false: {@code (start &lt;= value &lt;= end)}.
     *
     * @see java.util.Calendar
     * @see Date
     */
    public static SpinnerDateModel createDateAdapter(
            ValueModel valueModel,
            Date defaultDate,
            Comparable<Date> start, Comparable<Date> end, int calendarField) {
        checkNotNull(valueModel,  "The valueModel must not be null.");
        checkNotNull(defaultDate, "The default date must not be null.");
        Date valueModelDate = (Date) valueModel.getValue();
        Date initialDate = valueModelDate != null
            ? valueModelDate
            : defaultDate;
        SpinnerDateModel spinnerModel = new SpinnerDateModel(initialDate,
                start, end, calendarField);
        connect(spinnerModel, valueModel, defaultDate);
        return spinnerModel;
    }


    /**
     * Creates and returns a {@link SpinnerNumberModel} that is connected to
     * the given {@link ValueModel} and that honors the specified minimum,
     * maximum and step values.
     *
     * @param valueModel   an {@code Integer} typed model that holds the spinner value
     * @param defaultValue the number used if the valueModel's value is {@code null}
     * @param minValue     the lower bound of the spinner number
     * @param maxValue     the upper bound of the spinner number
     * @param stepSize     used to increment and decrement the current value
     * @return a {@code SpinnerNumberModel} that is connected to the given
     *     {@code ValueModel}
     *
     * @throws NullPointerException   if the valueModel is {@code null}
     */
    public static SpinnerNumberModel createNumberAdapter(
            ValueModel valueModel,
            int defaultValue,
            int minValue, int maxValue, int stepSize) {
        return createNumberAdapter(valueModel,
                Integer.valueOf(defaultValue),
                Integer.valueOf(minValue), Integer.valueOf(maxValue),
                Integer.valueOf(stepSize));
    }


    /**
     * Creates and returns a {@link SpinnerNumberModel} that is connected to
     * the given {@link ValueModel} and that honors the specified minimum,
     * maximum and step values.
     *
     * @param valueModel   a {@code Number} typed model that holds the spinner value
     * @param defaultValue the number used if the valueModel's value is {@code null}
     * @param minValue     the lower bound of the spinner number
     * @param maxValue     the upper bound of the spinner number
     * @param stepSize     used to increment and decrement the current value
     * @return a {@code SpinnerNumberModel} that is connected to the given
     *     {@code ValueModel}
     *
     * @throws NullPointerException  if the valueModel or defaultValue is {@code null}
     */
    public static SpinnerNumberModel createNumberAdapter(
            ValueModel valueModel,
            Number defaultValue,
            Comparable<? extends Number> minValue, Comparable<? extends Number> maxValue, Number stepSize) {
        Number valueModelNumber = (Number) valueModel.getValue();
        Number initialValue = valueModelNumber != null
            ? valueModelNumber
            : defaultValue;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
                initialValue,
                minValue, maxValue, stepSize);
        connect(spinnerModel, valueModel, defaultValue);
        return spinnerModel;
    }


    // Connecting a ValueModel with a General SpinnerModel*********************

    /**
     * Connects the given ValueModel and SpinnerModel
     * by synchronizing their values.
     *
     * @param spinnerModel  the underlying SpinnerModel implementation
     * @param valueModel    provides a value
     * @param defaultValue  the value used if the valueModel's value is {@code null}
     * @throws NullPointerException
     *     if the spinnerModel, valueModel or defaultValue is {@code null}
     */
    public static void connect(SpinnerModel spinnerModel, ValueModel valueModel, Object defaultValue) {
        SpinnerToValueModelConnector.connect(spinnerModel, valueModel, defaultValue);
    }
}
