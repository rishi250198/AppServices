package com.poc.authservice.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poc.authservice.exception.EmployeeNotFoundException;
import com.poc.authservice.model.Employee;
import com.poc.authservice.model.Role;
import com.poc.authservice.repository.AuthRepository;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthRepository authRepository;
	private static final Logger logger= LogManager.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Employee loginService(String email, String password) throws Exception {
		EmployeeNotFoundException employeeNotFoundException;
		Exception exception;
		
		if(email==null || password==null || email.trim().length()==0 || password.trim().length()==0) {
			exception = new Exception("Email or password cannot be empty");
			logger.error(exception);
			throw exception;
		}
		
		logger.debug("Email and password are not null or empty");
		
		logger.debug(email);
		Employee employee = authRepository.findByEmail(email);
		
		if(null==employee ) {
			employeeNotFoundException = new EmployeeNotFoundException("Unable to login the employee. Please check email and password provided");
			logger.error("Unsuccessful Login", password, employee, employee, employee, employeeNotFoundException, email, password, employee);
			throw employeeNotFoundException;
		}
		else {
			String empPassword = employee.getPassword().trim();
			if(!passwordEncoder.matches(password, empPassword)) {
				employeeNotFoundException = new EmployeeNotFoundException("Unable to login the employee. Please check email and password provided");
				logger.error(employeeNotFoundException);
				throw employeeNotFoundException;
			}	
		}
		
		logger.debug("Found employee",employee);
		return employee;
	}
	
	@Override
	public Employee registrationService(Employee employee) throws Exception {
		Exception exception;
		logger.debug(employee);
		if(employee.getEmail()==null || employee.getPassword()==null || employee.getFirstName()==null || employee.getLastName()==null || employee.getGender()==null || employee.getRole()==null) {
			exception = new Exception("Employee fields cannot be null");
			logger.error(exception);
			throw exception;
		}
		else if(employee.getEmail().length()==0 || !employee.getEmail().contains("@") || employee.getFirstName().length()==0 || employee.getLastName().length()==0 || employee.getGender().length()==0 || employee.getPassword().length()==0 || employee.getRole().length()==0) {		
			exception = new Exception("Employee fields cannot be empty and email must have @ ");
			logger.error(exception);
			throw exception;
		}
		else if(employee.getPassword().length()<6) {
			exception = new Exception("Password must contain more than 6 charecters");
			logger.error(exception);
			throw exception;
		}
		else if(!employee.getRole().trim().toLowerCase().equals(Role.MANAGER.getValue()) && !employee.getRole().trim().toLowerCase().equals(Role.ADMIN.getValue()) && !employee.getRole().trim().toLowerCase().equals("associate")) {
			exception = new Exception("Invalid role provided");
			logger.error(exception);
			throw exception;
		}
		
		logger.debug(employee.getEmail().toLowerCase().trim());
		Employee existingEmployee = authRepository.findByEmail(employee.getEmail().toLowerCase().trim());
		logger.debug("Found employee",employee);	
		
		if(existingEmployee!=null) {
			exception = new Exception("Employee already exists");
			logger.error(exception);
			throw exception;
		}
		String encodedPassword = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(encodedPassword);
		
		logger.debug(employee);
		Employee lowerCaseEmployee =new Employee();
		lowerCaseEmployee.setEmail(employee.getEmail().trim().toLowerCase());
		lowerCaseEmployee.setFirstName(employee.getFirstName().trim().toLowerCase());
		lowerCaseEmployee.setLastName(employee.getLastName().trim().toLowerCase());
		lowerCaseEmployee.setGender(employee.getGender().trim().toLowerCase());
		lowerCaseEmployee.setPassword(employee.getPassword().trim());
		lowerCaseEmployee.setRole(employee.getRole().trim().toLowerCase());
		lowerCaseEmployee.setSkills(employee.getSkills());
		logger.debug( "Employee fields converted to lower case",lowerCaseEmployee);
		Employee createdEmployee = authRepository.save(lowerCaseEmployee);
		logger.debug( "Employee created",createdEmployee);
		
		if(createdEmployee==null) {
			exception = new Exception("Unable to create employee");
			logger.error(exception);
			throw exception;
		}
		
		return createdEmployee;
	}

	public AuthServiceImpl(AuthRepository dao) {
		this.authRepository=dao;
	}
}
