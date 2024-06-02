package com.poc.adminservice.rest;

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

import com.poc.adminservice.model.Template;
import com.poc.adminservice.service.AdminService;

@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/admin")
public class AdminRestController {

	@Autowired
	private AdminService adminService;
	
	private static final Logger logger= LogManager.getLogger(AdminRestController.class);
	
	@PostMapping("/templates/get")
	public ResponseEntity<List<Template>> getTemplates() throws Exception {
		
		logger.debug("Getting all templates");
		List<Template> templates = adminService.getTemplates();
		logger.debug(templates);
		return new ResponseEntity<>(templates, HttpStatus.OK);
	}
	
	@PostMapping("/template/update")
	public ResponseEntity<Template> updateTemplate( @RequestBody Template template) throws Exception {
		
		logger.debug(template);
		Template updatedTemplate = adminService.updateTemplate(template);
		logger.debug(updatedTemplate);
		return new ResponseEntity<>(updatedTemplate, HttpStatus.OK);	
	}
	
	@GetMapping("/test")
	public String test() {
		return "done";
	}
	
}
