package com.pinguin.validation;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.beans.PropertyNotFoundException;
import com.pinguin.util.ReflectionUtil;

public class PresentationModel2<T> extends PresentationModel<T> {
	private static final long serialVersionUID = 1L;

	public PresentationModel2(T bean) {
		super(bean);
	}

	/**
	 * Adiciona o listener para modificacoes nas propriedades do buffer.
	 * 
	 * @param listener Instancia que implemente {@link PropertyChangeListener}.
	 */
	public void addBufferPropValueChangeListener(PropertyChangeListener listener) {
		for (Field field : ReflectionUtil.getAllFields(getBean().getClass())) {
			try {
				getBufferedModel(field.getName()).addValueChangeListener(
						listener);
			} catch (PropertyNotFoundException e) {
				// DO NOTHING
			}
		}
	}
	
	/**
	 * Obtem uma copia do bean.
	 * 
	 * @return Copia do bean.
	 */
	@SuppressWarnings("unchecked")
	public T getBuffer() {
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
			
			return (T) copy;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
