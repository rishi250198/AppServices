package com.poc.managerService.exception;

public class InvalidEmailAddressException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidEmailAddressException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEmailAddressException(String message) {
		super(message);
	}

	public InvalidEmailAddressException(Throwable cause) {
		super(cause);
	}
	
}