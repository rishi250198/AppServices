package com.poc.adminservice.exception;

public class TemplateNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TemplateNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateNotFoundException(String message) {
		super(message);
	}

	public TemplateNotFoundException(Throwable cause) {
		super(cause);
	}
	
}