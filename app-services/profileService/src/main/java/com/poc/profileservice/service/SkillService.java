package com.poc.profileservice.service;

import java.util.List;

import com.poc.profileservice.model.Skill;
import com.poc.profileservice.model.SkillRequest;

public interface SkillService {

	List<Skill> addSkills( List<SkillRequest> skills) throws Exception;
	List<Skill> getSkills( int employeeId ) throws Exception;
	
}
