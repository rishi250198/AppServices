package com.poc.authservice.service;

import com.poc.authservice.model.Employee;

public interface AuthService {
	Employee loginService(String email, String password) throws Exception;
	Employee registrationService(Employee employee) throws Exception;
}
