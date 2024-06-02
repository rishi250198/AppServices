package com.poc.managerService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.managerService.model.Employee;
import com.poc.managerService.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
	
	Skill findById(int id);
	List<Skill> findByEmployeeAndStatus(Employee employee, String status);

}
