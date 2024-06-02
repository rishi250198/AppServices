package com.poc.authservice.exception;

public class ManagerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ManagerNotFoundException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public ManagerNotFoundException(String message) {
		super(message);
	}
	
	public ManagerNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
