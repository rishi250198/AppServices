package com.poc.adminservice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.adminservice.exception.TemplateNotFoundException;
import com.poc.adminservice.model.Template;
import com.poc.adminservice.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	
	@Override
	public List<Template> getTemplates() throws Exception {
		
		List<Template> templates = adminRepository.findAll();
		if(templates == null) {
			TemplateNotFoundException exception = new TemplateNotFoundException("No templates found in database");
			logger.debug(templates);
			throw exception;
		}
		logger.debug(templates);
		return templates;
	}

	@Override
	public Template updateTemplate(Template template) throws Exception {
		
		if(template == null) {
			TemplateNotFoundException exception = new TemplateNotFoundException("Template cannot be empty");
			logger.debug(exception);
			throw exception;
		}
		logger.debug(template);
		
		Template originalTemplate = adminRepository.findByStatus(template.getStatus());
		if(originalTemplate == null) {
			TemplateNotFoundException exception = new TemplateNotFoundException("Template not found in database");
			logger.debug(exception);
			throw exception;
		}
		logger.debug(originalTemplate);
		originalTemplate.setFromAddress(template.getFromAddress().trim());
		originalTemplate.setToAddress(template.getToAddress().trim());
		originalTemplate.setCc(template.getCc().trim());
		originalTemplate.setSubject(template.getSubject().trim());
		originalTemplate.setBodyContent(template.getBodyContent().trim());
		originalTemplate.setCreatedBy(template.getCreatedBy());
		adminRepository.save(originalTemplate);
		
		return originalTemplate;
	}

}
