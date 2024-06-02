package com.poc.managerService.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.poc.managerService.exception.EmployeeNotFoundException;
import com.poc.managerService.exception.InvalidEmailAddressException;
import com.poc.managerService.exception.InvalidSkillIdException;
import com.poc.managerService.exception.ManagerRoleException;
import com.poc.managerService.exception.SkillNotFoundException;
import com.poc.managerService.model.Employee;
import com.poc.managerService.model.GetManagerResponse;
import com.poc.managerService.model.Role;
import com.poc.managerService.model.Skill;
import com.poc.managerService.model.Status;
import com.poc.managerService.model.Template;
import com.poc.managerService.repository.EmployeeRepository;
import com.poc.managerService.repository.SkillRepository;
import com.poc.managerService.repository.TemplateRepository;


@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private TemplateRepository templateRepository;
	
	private static final Logger logger= LogManager.getLogger(ManagerServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public List<GetManagerResponse> getSkills(int managerId) throws Exception {
		
		Employee manager = employeeRepository.findById(managerId);
		
		if(manager == null) {
			
			EmployeeNotFoundException exception = new EmployeeNotFoundException("Unable to find manager");
			logger.debug(exception);
			throw new Exception(exception);	
		}
		
		if(!manager.getRole().trim().toLowerCase().equals(Role.MANAGER.getValue())) {
			
			ManagerRoleException exception = new ManagerRoleException("User is not a manager");
			logger.debug(exception);
			throw new Exception(exception);	
		}
		
		logger.debug("Valid manager id");
		logger.debug(manager);
		List<Employee> employees = employeeRepository.findByManagerId(manager.getId());
		
		if(employees == null) {
			
			EmployeeNotFoundException exception = new EmployeeNotFoundException("Unable to find employees");
			logger.debug(exception);
			throw new Exception(exception);				
		}
		
		logger.debug("Manager is mapped to employees: " + employees);
		List<GetManagerResponse> pendingSkills = new ArrayList<>();

		for(Employee employee : employees) {
			
			List<Skill> skills = skillRepository.findByEmployeeAndStatus(employee, Status.PENDING_APPROVAL.getValue());
			if(skills != null) {
				
				for(Skill skill : skills) {
					
					GetManagerResponse tempSkill = new GetManagerResponse();
					tempSkill.setId(skill.getId());
					tempSkill.setName(skill.getName());
					tempSkill.setFamily(skill.getFamily());
					tempSkill.setProficiency(skill.getProficiency());
					tempSkill.setRoleType(skill.getRoleType());
					tempSkill.setExperience(skill.getExperience());
					tempSkill.setCurrentHistoric(skill.getCurrentHistoric());
					tempSkill.setApproverComments(skill.getApproverComments());
					tempSkill.setExperienceExposureText(skill.getExperienceExposureText());
					tempSkill.setStatus(skill.getStatus());
					tempSkill.setEmployeeId(employee.getId());
					
					pendingSkills.add(tempSkill);	
				}	
			}	
		}
		logger.debug("List of skills pending approval: " + pendingSkills);
		return pendingSkills;
	}
	
	@Override
	public List<Skill> updateSkills(List<Integer> skillIds, String status, String approverComments) throws Exception {
		
		if(skillIds == null) {
			
			SkillNotFoundException exception = new SkillNotFoundException("Unable to find skills");
			logger.debug(exception);
			throw new Exception(exception);	
		}
		
		List<Skill> skills = new ArrayList<>();
		
		for(int i : skillIds) {
			
			Skill skill = skillRepository.findById(i);
			
			if(skill == null) {
				
				InvalidSkillIdException exception = new InvalidSkillIdException("Invalid skill id");
				logger.debug(exception);
				throw new Exception(exception);	
			}
			
			skills.add(skill);		
		}
		
		logger.debug("List of skills: " + skills);
		List<Skill> updatedSkills = new ArrayList<>();
		
		for(Skill skill : skills) {
			
			Skill originalSkill = skillRepository.findById(skill.getId());
			
			if(originalSkill == null) {
				
				SkillNotFoundException exception = new SkillNotFoundException("Skills not available in database");
				logger.debug(exception);
				throw new Exception(exception);	
			}
			
			originalSkill.setStatus(status);
			originalSkill.setApproverComments(approverComments);
			skillRepository.save(originalSkill);
			
			Employee employee = originalSkill.getEmployee();
			Employee manager = employeeRepository.findById(employee.getManagerId());
			Template template = templateRepository.findByStatus(status);
			
			String from = template.getFromAddress().trim().toLowerCase();
			String to = "";
			String cc = "";
			String bodyContent = template.getBodyContent().trim();
			String[] bodyParts = bodyContent.split("#");
			String bodyPart1 = bodyParts[0];
			String bodyPart2 = bodyParts[1];
			
			if(template.getToAddress().trim().toLowerCase().equals(Role.MANAGER.getValue())) {
				to = manager.getEmail().trim().toLowerCase();
			} else if(template.getToAddress().trim().toLowerCase().equals(Role.ASSOCIATE.getValue())) {
				to = employee.getEmail().trim().toLowerCase();
			} else {
				InvalidEmailAddressException exception = new InvalidEmailAddressException("Invalid to address in template");
				logger.debug(exception);
				throw exception;
			}
			
			if(template.getCc().trim().toLowerCase().equals(Role.MANAGER.getValue())) {
				cc = manager.getEmail().trim().toLowerCase();
			} else if(template.getCc().trim().toLowerCase().equals(Role.ASSOCIATE.getValue())) {
				cc = employee.getEmail().trim().toLowerCase();
			} else {
				InvalidEmailAddressException exception = new InvalidEmailAddressException("Invalid cc address in template");
				logger.debug(exception);
				throw exception;
			}
			
			bodyPart1 += "<tr align='center'>" 
					+ "<td>" + StringUtils.capitalize(originalSkill.getName().trim()) + "</td>"
					+ "<td>" + StringUtils.capitalize(originalSkill.getFamily().trim()) + "</td>"
					+ "<td>" + StringUtils.capitalize(originalSkill.getProficiency()) + "</td>"
					+ "<td>" + originalSkill.getExperience() + "</td>"
					+ "<td>" + StringUtils.capitalize(originalSkill.getCurrentHistoric()) + "</td></tr>";
			bodyPart1 += bodyPart2;
			
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			helper.setSubject(StringUtils.capitalize(template.getSubject()));
			helper.setFrom(from);
			helper.setTo(to);
			helper.setCc(cc);
			helper.setText(bodyPart1, true);	
			
			mailSender.send(message);
			
			updatedSkills.add(originalSkill);		
		}
		logger.debug("Updated skills: " + updatedSkills);
		return updatedSkills;
	}
}
