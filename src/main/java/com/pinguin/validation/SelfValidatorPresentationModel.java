package com.pinguin.validation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.beans.PropertyNotFoundException;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.pinguin.util.ReflectionUtil;

/**
 * TODO:<br>
 * 1. Separar responsabilidades. Fazer com que este seja apenas um notificador
 * de mudanças.<br>
 * 
 * @param <T>
 */
public class SelfValidatorPresentationModel<T> extends PresentationModel<T> {

	private static final long serialVersionUID = 1L;

	private final ValidationResultModel validationResultModel = new DefaultValidationResultModel();
	private final Validator<T> validator;

	public SelfValidatorPresentationModel(T bean, Validator<T> validator) {
		super(bean);

		this.validator = validator;

		if (validator != null) {
			initEventHandling();
			updateValidationResult();
		}
	}

	public SelfValidatorPresentationModel(T bean) {
		this(bean, null);
	}

	private void initEventHandling() {
		PropertyChangeListener propertyValueHandler = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateValidationResult();
			}
		};

		for (Field field : ReflectionUtil.getAllFields(getBean().getClass())) {
			try {
				getBufferedModel(field.getName()).addValueChangeListener(
						propertyValueHandler);
			} catch (PropertyNotFoundException e) {
				// DO NOTHING
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void updateValidationResult() {
		try {
			// criar uma instancia do mesmo tipo
			final Object copy = getBean().getClass().newInstance();
			// copiar os valores
			for (Field field : ReflectionUtil
					.getAllFields(getBean().getClass())) {
				try {
					field.setAccessible(true);
					field.set(copy, getBufferedModel(field.getName())
							.getValue());
				} catch (PropertyNotFoundException e) {
					// DO NOTHING
				} catch (IllegalAccessException e) {
					// DO NOTHING
				}
			}
			// validar
			if (validator != null) {
				getValidationResultModel().setResult(
						validator.validate((T) copy));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ValidationResultModel getValidationResultModel() {
		return validationResultModel;
	}
}
