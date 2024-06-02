package com.poc.adminservice.service;

import java.util.List;

import com.poc.adminservice.model.Template;

public interface AdminService {
	
	List<Template> getTemplates() throws Exception;
	Template updateTemplate(Template template) throws Exception;

}
