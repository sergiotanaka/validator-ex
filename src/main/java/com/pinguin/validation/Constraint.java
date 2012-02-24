package com.pinguin.validation;

public abstract class Constraint {
	private String propertyName;
	private String errorMessage;
	private String warningMessage;
	
	public Constraint(String propertyName, String errorMsg) {
		setPropertyName(propertyName);
		setErrorMessage(errorMsg);
	}

	public Constraint(String propertyName, String errorMsg, String warningMsg) {
		this(propertyName, errorMsg);
		setWarningMessage(warningMsg);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public abstract boolean isWrong(Object value);

	public boolean needAttention(Object value) {
		return false;
	}

}
