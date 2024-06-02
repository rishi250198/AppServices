package com.poc.adminservice.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import com.poc.adminservice.model.Template;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AdminRestControllerTest {
	
	@Value(value="${local.server.port}")
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value(value="${api.test.url}")
	private String testUrl;
	
	@Value(value="${api.gettemplates.url}")
	private String getTemplatesUrl;
	
	@Value(value="${api.updatetemplate.url}")
	private String updateTemplateUrl;

	
	@Test
	public void testShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject(testUrl,String.class)).contains("done");
	}
	
	 //Tests for Get Templates API in AdminRestController
	@Test
	public void testGetTemplates() throws Exception {
		HttpStatus result = this.restTemplate.postForEntity(getTemplatesUrl, null, String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	
	 //Tests for Update Template API in AdminRestController
	@Test
	public void testUpdateTemplateValidStatus() throws Exception {
		Template template = new Template(0, "test", "test", "test", "test subject", "test body content", 50, "approved");
		HttpStatus result = this.restTemplate.postForEntity(updateTemplateUrl, new HttpEntity<Template>(template), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	@Test
	public void testUpdateTemplateInvalidStatus() throws Exception {
		Template template = new Template(0, "test", "test", "test", "test subject", "test body content", 50, "test");
		HttpStatus result = this.restTemplate.postForEntity(updateTemplateUrl, new HttpEntity<Template>(template), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND);
	}
	@Test
	public void testUpdateTemplateInvalidFormat() throws Exception {
		HttpStatus result = this.restTemplate.postForEntity(updateTemplateUrl, null, String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
