package com.poc.profileservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.profileservice.model.Employee;
import com.poc.profileservice.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
	
	List<Skill> findByEmployee(Employee employee);

}
