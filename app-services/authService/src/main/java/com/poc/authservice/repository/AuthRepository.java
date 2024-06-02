package com.poc.authservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.authservice.model.Employee;

@Repository
public interface AuthRepository extends JpaRepository<Employee, Integer> {
	Employee findByEmail(String emailId);
	Employee findById(int employeeId);
	List<Employee> findByRole(String role);
	
}
