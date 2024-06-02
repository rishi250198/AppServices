package com.poc.authservice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.authservice.exception.EmployeeNotFoundException;
import com.poc.authservice.exception.ManagerNotFoundException;
import com.poc.authservice.model.Employee;
import com.poc.authservice.model.Role;
import com.poc.authservice.repository.AuthRepository;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private AuthRepository authRepository;
	
	private static final Logger logger= LogManager.getLogger(ManagerServiceImpl.class);
	
	@Override
	public List<Employee> getManagers() throws Exception {
		List<Employee> managerList = authRepository.findByRole(Role.MANAGER.getValue());
		if(managerList.size() == 0) {
			ManagerNotFoundException exception = new ManagerNotFoundException("No managers found in database.");
			logger.debug(exception);
			throw(exception);
		}
		
		logger.debug("Manager list is not empty");
		
		return managerList;
	}
	
	@Override
	public Employee updateManager(String managerEmail, int employeeId) throws Exception {
		if(managerEmail.trim().length() == 0) {
			Exception exception = new Exception("Invalid manager id.");
			logger.debug(exception);
			throw(exception);
		}
		if(employeeId == 0) {
			Exception exception = new Exception("Invalid employee id.");
			logger.debug(exception);
			throw(exception);
		}
		Employee employee = authRepository.findById(employeeId);
		if(employee == null) {
			EmployeeNotFoundException employeeNotFoundException = new EmployeeNotFoundException("Employee email not found");
			logger.debug(employeeNotFoundException);
			throw(employeeNotFoundException);
		}
		
		logger.debug("Employee email id and manager id not empty");
		
		Employee manager = authRepository.findByEmail(managerEmail);
		if(!manager.getRole().equals(Role.MANAGER.getValue())) {
			EmployeeNotFoundException employeeNotFoundException = new EmployeeNotFoundException("Manager not found in database");
			logger.debug(employeeNotFoundException);
			throw(employeeNotFoundException);
		}
		employee.setManagerId(manager.getId());
		
		Employee updatedEmployee = authRepository.save(employee);
		
		if(updatedEmployee == null) {
			Exception exception = new Exception("Unable to update employee");
			logger.debug(exception);
			throw(exception);
		}
		
		logger.debug("Updated employee");
		
		return updatedEmployee;
	}

}
