package com.skill.exception;


public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	// “Ï≥£–≈œ¢
	private String message;

	public CustomException(String message) {
		super(message);
		this.message = message;

	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}