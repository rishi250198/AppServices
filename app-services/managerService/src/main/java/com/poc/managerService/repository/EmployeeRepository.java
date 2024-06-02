package com.poc.managerService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.managerService.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	Employee findById(int employeeId);
	
	List<Employee> findByManagerId(int managerId);

}
