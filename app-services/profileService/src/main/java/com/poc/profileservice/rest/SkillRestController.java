package com.poc.profileservice.rest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.profileservice.model.GetSkillRequest;
import com.poc.profileservice.model.Skill;
import com.poc.profileservice.model.SkillRequest;
import com.poc.profileservice.service.SkillService;

@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/skill")
public class SkillRestController {

	@Autowired
	private SkillService skillService;
	
	private static final Logger logger= LogManager.getLogger(SkillRestController.class);
	
	@PostMapping("/add")
	public ResponseEntity<List<Skill>> addSkills( @RequestBody List<SkillRequest> skillRequest) throws Exception {
		
		logger.debug(skillRequest);
		
		List<Skill> skillsList = skillService.addSkills(skillRequest);
		
		logger.debug("List of skills to be added");
		logger.debug(skillsList);
		
		return new ResponseEntity<>(skillsList, HttpStatus.OK);
	}
	
	@PostMapping("/get")
	public ResponseEntity<List<Skill>> getSkills( @RequestBody GetSkillRequest getSkillRequest) throws Exception {
		
		logger.debug(getSkillRequest);
		
		int employeeId = getSkillRequest.getEmployeeId();
		
		List<Skill> skills = skillService.getSkills(employeeId);
		
		return new ResponseEntity<>(skills, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String test() {
		return "done";
	}
}
