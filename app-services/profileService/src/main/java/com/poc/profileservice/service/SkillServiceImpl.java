package com.poc.profileservice.service;

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

import com.poc.profileservice.exception.EmployeeNotFoundException;
import com.poc.profileservice.exception.InvalidEmailAddressException;
import com.poc.profileservice.model.Employee;
import com.poc.profileservice.model.Role;
import com.poc.profileservice.model.Skill;
import com.poc.profileservice.model.SkillRequest;
import com.poc.profileservice.model.Status;
import com.poc.profileservice.model.Template;
import com.poc.profileservice.repository.EmployeeRepository;
import com.poc.profileservice.repository.SkillRepository;
import com.poc.profileservice.repository.TemplateRepository;

@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private TemplateRepository templateRepository;
	
	private static final Logger logger= LogManager.getLogger(SkillServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public List<Skill> addSkills( List<SkillRequest> skills) throws Exception {
		
		List<Skill> skillsList = new ArrayList<>();
		
		Template template = templateRepository.findByStatus(Status.SUBMITTED.getValue());
		String from = template.getFromAddress().trim().toLowerCase();
		String to = "";
		String cc = "";
		String bodyContent = "";
		String bodyPart1 = "";
		String bodyPart2 = "";
		String bodyPart3 = "";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		for(SkillRequest skill : skills) {
			int employeeId = skill.getEmployeeId();
			Employee employee = employeeRepository.findById(employeeId);
			
			if(employee == null) {
				EmployeeNotFoundException exception = new EmployeeNotFoundException("Employee not found");
				logger.debug(exception);
				throw(exception);
			}
			
			Skill newSkill = new Skill();
			newSkill.setName(skill.getName().trim().toLowerCase());
			newSkill.setFamily(skill.getFamily().trim().toLowerCase());
			newSkill.setProficiency(skill.getProficiency().trim().toLowerCase());
			newSkill.setRoleType(skill.getRoleType().trim().toLowerCase());
			newSkill.setExperience(skill.getExperience());
			newSkill.setCurrentHistoric(skill.getCurrentHistoric().trim().toLowerCase());
			newSkill.setApproverComments(skill.getApproverComments().trim().toLowerCase());
			newSkill.setExperienceExposureText(skill.getExperienceExposureText().trim().toLowerCase());
			newSkill.setStatus(skill.getStatus().trim().toLowerCase());
			newSkill.setEmployee(employee);
			
			Skill createdSkill = skillRepository.save(newSkill);
			if(createdSkill == null) {
				Exception exception = new Exception("Unable to create skills");
				logger.debug(exception);
				throw(exception);
			}
			
			skillsList.add(createdSkill);
						
			Employee manager = employeeRepository.findById(employee.getManagerId());
			
			
			if(to.isEmpty()) {
				if(template.getToAddress().trim().toLowerCase().equals(Role.MANAGER.getValue())) {
					to = manager.getEmail().trim().toLowerCase();
				} else if(template.getToAddress().trim().toLowerCase().equals(Role.ASSOCIATE.getValue())) {
					to = employee.getEmail().trim().toLowerCase();
				} else {
					InvalidEmailAddressException exception = new InvalidEmailAddressException("Invalid to address in template");
					logger.debug(exception);
					throw exception;
				}
			}
			
			if(cc.isEmpty()) {
				if(template.getCc().trim().toLowerCase().equals(Role.MANAGER.getValue())) {
					cc = manager.getEmail().trim().toLowerCase();
				} else if(template.getCc().trim().toLowerCase().equals(Role.ASSOCIATE.getValue())) {
					cc = employee.getEmail().trim().toLowerCase();
				} else {
					InvalidEmailAddressException exception = new InvalidEmailAddressException("Invalid cc address in template");
					logger.debug(exception);
					throw exception;
				}
			}
			
			if(bodyContent.isEmpty()) {
				bodyContent = template.getBodyContent().trim();
				String[] bodyParts = bodyContent.split("#");
				bodyPart1 = bodyParts[0];
				bodyPart2 = bodyParts[1];
				bodyPart3 = bodyParts[2];
				bodyPart1 += "<tr align='center'>"
								+ "<td>" + employee.getId() + "</td>"
								+ "<td>" + StringUtils.capitalize(employee.getFirstName()) + " " + StringUtils.capitalize(employee.getLastName()) + "</td>"
								+ "<td>" + employee.getManagerId() + "</td>"
								+ "<td>" + StringUtils.capitalize(Status.SUBMITTED.getValue());
			}
			
			bodyPart2 += "<tr align='center'>" 
							+ "<td>" + StringUtils.capitalize(createdSkill.getName().trim()) + "</td>"
							+ "<td>" + StringUtils.capitalize(createdSkill.getFamily().trim()) + "</td>"
							+ "<td>" + StringUtils.capitalize(createdSkill.getProficiency()) + "</td>"
							+ "<td>" + createdSkill.getExperience() + "</td>"
							+ "<td>" + StringUtils.capitalize(createdSkill.getCurrentHistoric()) + "</td></tr>";
		}
		
		bodyPart1 += bodyPart2;
		bodyPart1 += bodyPart3;
		
		helper.setSubject(StringUtils.capitalize(template.getSubject()));
		helper.setFrom(from);
		helper.setTo(to);
		helper.setCc(cc);
		helper.setText(bodyPart1, true);	
		
		mailSender.send(message);
		
		return skillsList;
	}
	
	@Override
	public List<Skill> getSkills(int employeeId) throws Exception {
		
		Employee employee = employeeRepository.findById(employeeId);
		if(employee == null) {
			EmployeeNotFoundException exception = new EmployeeNotFoundException("Employee not found");
			logger.debug(exception);
			throw(exception);
		}
		
		List<Skill> skills = skillRepository.findByEmployee(employee);
		
		return skills;
		
	}
	
}
