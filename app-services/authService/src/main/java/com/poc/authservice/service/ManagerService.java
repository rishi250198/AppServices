package com.poc.authservice.service;

import java.util.List;

import com.poc.authservice.model.Employee;

public interface ManagerService {

	List<Employee> getManagers() throws Exception;
	Employee updateManager(String managerEmail, int employeeId) throws Exception;
	
}
