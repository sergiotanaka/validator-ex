package com.pinguin.validation;

import com.jgoodies.validation.ValidationResult;

public class NhanhaValidatorTest {

	public static void main(String[] args) {

		Nhanha bean = new Nhanha();
		bean.setValue(34.0);
		SelfValidatorPresentationModel<Nhanha> presentationModel = new SelfValidatorPresentationModel<Nhanha>(
				bean, null);
		presentationModel.setBufferedValue("value", -33.0);
		presentationModel.setBufferedValue("value", -5.0);
		ValidationResult validationResult = presentationModel.getValidationResultModel().getResult();
		System.out.println(validationResult.getMessagesText());
		
		

	}
}
