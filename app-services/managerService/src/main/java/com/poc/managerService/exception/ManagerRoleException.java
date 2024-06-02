package com.poc.managerService.exception;

public class ManagerRoleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ManagerRoleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagerRoleException(String message) {
		super(message);
	}

	public ManagerRoleException(Throwable cause) {
		super(cause);
	}
	
}
