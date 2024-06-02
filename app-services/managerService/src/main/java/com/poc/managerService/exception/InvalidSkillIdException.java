package com.poc.managerService.exception;

public class InvalidSkillIdException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidSkillIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSkillIdException(String message) {
		super(message);
	}

	public InvalidSkillIdException(Throwable cause) {
		super(cause);
	}
	
}
