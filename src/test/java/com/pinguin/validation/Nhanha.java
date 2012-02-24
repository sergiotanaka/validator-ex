package com.pinguin.validation;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.validation.Validatable;
import com.jgoodies.validation.ValidationResult;

public class Nhanha extends Model implements Validatable {
	private static final long serialVersionUID = 1L;
	
	private double value;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public ValidationResult validate() {
		ValidationResult validationResult = new ValidationResult();
		
		if (this.value < 0) {
			validationResult.addError("O valor deve ser maior que zero.");
		}
		
		return validationResult;
	}
}
