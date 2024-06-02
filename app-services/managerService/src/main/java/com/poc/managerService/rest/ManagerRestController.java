package com.poc.managerService.rest;

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

import com.poc.managerService.model.GetManagerRequest;
import com.poc.managerService.model.GetManagerResponse;
import com.poc.managerService.model.Skill;
import com.poc.managerService.model.SkillRequest;
import com.poc.managerService.service.ManagerService;

@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("manager")
public class ManagerRestController {
	
	@Autowired
	private ManagerService managerService;
	
	private static final Logger logger= LogManager.getLogger(ManagerRestController.class);
	
	@PostMapping("/skills/get")
	public ResponseEntity<List<GetManagerResponse>> getPendingSkills( @RequestBody GetManagerRequest getManagerRequest) throws Exception {
		
		int managerId = getManagerRequest.getManagerId();
		
		logger.debug("Manager Id received: " + managerId);
		List<GetManagerResponse> pendingSkills = managerService.getSkills(managerId);
		
		logger.debug("Pending skills: " + pendingSkills);
		return new ResponseEntity<>(pendingSkills, HttpStatus.OK);
	}
	
	@PostMapping("/skills/approve")
	public ResponseEntity<List<Skill>> approveSkills( @RequestBody SkillRequest skillRequest) throws Exception {
		
		List<Integer> skillIds = skillRequest.getSkillIds();
		String approverComments = skillRequest.getApproverComments();
		logger.debug("Skill ids received: " + skillIds);
		logger.debug("Approver comments: " + approverComments);
		
		List<Skill> updatedSkills = managerService.updateSkills(skillIds, "approved", approverComments);
		
		logger.debug("Approved skills: " + updatedSkills);
		return new ResponseEntity<>(updatedSkills, HttpStatus.OK);
		
	}
	
	@PostMapping("/skills/reject")
	public ResponseEntity<List<Skill>> rejectSkills( @RequestBody SkillRequest skillRequest) throws Exception {
		
		List<Integer> skillIds = skillRequest.getSkillIds();
		String approverComments = skillRequest.getApproverComments();
		logger.debug("Skill ids received: " + skillIds);
		logger.debug("ApproverComments: " + approverComments);
		
		List<Skill> updatedSkills = managerService.updateSkills(skillIds, "rejected", approverComments);
		
		logger.debug("Rejected Skills: " + updatedSkills);
		return new ResponseEntity<>(updatedSkills, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String test() {
		return "done";
	}

}
