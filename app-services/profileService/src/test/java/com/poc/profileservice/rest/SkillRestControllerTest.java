package com.poc.profileservice.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

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

import com.poc.profileservice.model.GetSkillRequest;
import com.poc.profileservice.model.SkillRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class SkillRestControllerTest {
	
	@Value(value="${local.server.port}")
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value(value="${api.test.url}")
	private String testUrl;
	
	@Value(value="${api.getskills.url}")
	private String getSkillsUrl;
	
	@Value(value="${api.addskills.url}")
	private String addSkillsUrl;
	
	//Test for the Test API in SkillRestController
	@Test
	public void testShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject(testUrl,String.class)).contains("done");
	}
		
	//Tests for Get Skills API in SkillRestController
	@Test
	public void testGetSkillsEmployee() throws Exception {
		GetSkillRequest getSkillRequest = new GetSkillRequest(54);
		HttpStatus result = this.restTemplate.postForEntity(getSkillsUrl, new HttpEntity<GetSkillRequest>(getSkillRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testGetSkillsInvalidEmployee() throws Exception {
		GetSkillRequest getSkillRequest = new GetSkillRequest(100);
		HttpStatus result = this.restTemplate.postForEntity(getSkillsUrl, new HttpEntity<GetSkillRequest>(getSkillRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	//Tests for Add Skills API in SkillRestController
	@Test
	public void testAddSkillSingleEmployee() throws Exception {
		List<SkillRequest> skillRequest = new ArrayList<>();
		skillRequest.add(new SkillRequest("Java", "Programming", "Advanced", "associate", 12, "Current", "", "", "pending approval", 56));
		HttpStatus result = this.restTemplate.postForEntity(addSkillsUrl, new HttpEntity<List<SkillRequest>>(skillRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testAddSkillInvalidFormatEmployee() throws Exception {
		SkillRequest skillRequest = new SkillRequest("Java", "Programming", "Advanced", "associate", 12, "Current", "", "", "pending approval", 56);
		HttpStatus result = this.restTemplate.postForEntity(addSkillsUrl, new HttpEntity<SkillRequest>(skillRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testAddMultipleSkillsSingleEmployee() throws Exception {
		List<SkillRequest> skillRequest = new ArrayList<>();
		skillRequest.add(new SkillRequest("C Sharp", "Programming", "Advanced", "Associate", 12, "Current", "", "", "pending approval", 56));
		skillRequest.add(new SkillRequest("Python", "Programming", "Advanced", "Associate", 12, "Current", "", "", "pending approval", 56));
		HttpStatus result = this.restTemplate.postForEntity(addSkillsUrl, new HttpEntity<List<SkillRequest>>(skillRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testAddSkillInvalidEmployee() throws Exception {
		List<SkillRequest> skillRequest = new ArrayList<>();
		skillRequest.add(new SkillRequest("Java", "Programming", "Advanced", "Associate", 12, "Current", "", "", "pending approval", 100));
		HttpStatus result = this.restTemplate.postForEntity(addSkillsUrl, new HttpEntity<List<SkillRequest>>(skillRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
