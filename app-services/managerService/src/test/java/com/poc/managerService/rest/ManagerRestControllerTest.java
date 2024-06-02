package com.poc.managerService.rest;

import static org.assertj.core.api.Assertions.assertThat;

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

import com.poc.managerService.model.GetManagerRequest;
import com.poc.managerService.model.SkillRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ManagerRestControllerTest {
	
	@Value(value="${local.server.port}")
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value(value="${api.test.url}")
	private String testUrl;
	
	@Value(value="${api.getskills.url}")
	private String getSkillsUrl;
	
	@Value(value="${api.approveskills.url}")
	private String approveSkillsUrl;
	
	@Value(value="${api.rejectskills.url}")
	private String rejectSkillsUrl;

	@Test
	public void testShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject(testUrl,String.class)).contains("done");
	}
	
	//Tests for Get Skills Manager API in ManagerRestController
	@Test
	public void testGetSkillsValidManager() throws Exception {
		GetManagerRequest getManagerRequest = new GetManagerRequest(55);
		HttpStatus result = this.restTemplate.postForEntity(getSkillsUrl, new HttpEntity<GetManagerRequest>(getManagerRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testGetSkillsInvalidManager() throws Exception {
		GetManagerRequest getManagerRequest = new GetManagerRequest(100);
		HttpStatus result = this.restTemplate.postForEntity(getSkillsUrl, new HttpEntity<GetManagerRequest>(getManagerRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testGetSkillsInvalidRoleManager() throws Exception {
		GetManagerRequest getManagerRequest = new GetManagerRequest(54);
		HttpStatus result = this.restTemplate.postForEntity(getSkillsUrl, new HttpEntity<GetManagerRequest>(getManagerRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	//Tests for Approve Skills API in ManagerRestController
	@Test
	public void testApproveSingleSkill() throws Exception {
		SkillRequest skills = new SkillRequest(List.of(65), "Approved");
		HttpStatus result = this.restTemplate.postForEntity(approveSkillsUrl, new HttpEntity<SkillRequest>(skills), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testApproveMultipleSkills() throws Exception {
		SkillRequest skills = new SkillRequest(List.of(66, 67), "Approved");
		HttpStatus result = this.restTemplate.postForEntity(approveSkillsUrl, new HttpEntity<SkillRequest>(skills), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testApproveInvalidFormatSkills() throws Exception {
		List<Integer> skills = List.of(19, 20);
		HttpStatus result = this.restTemplate.postForEntity(approveSkillsUrl, new HttpEntity<List<Integer>>(skills), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testApproveUnavailableSkills() throws Exception {
		SkillRequest skills = new SkillRequest(List.of(100), "Approved");
		HttpStatus result = this.restTemplate.postForEntity(approveSkillsUrl, new HttpEntity<SkillRequest>(skills), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	//Tests for Reject Skills API in ManagerRestController
		@Test
		public void testRejectSingleSkill() throws Exception {
			SkillRequest skills = new SkillRequest(List.of(68), "Rejected");
			HttpStatus result = this.restTemplate.postForEntity(rejectSkillsUrl, new HttpEntity<SkillRequest>(skills), String.class).getStatusCode();
			assertThat(result).isEqualTo(HttpStatus.OK);
		}
		
		@Test
		public void testRejectMultipleSkills() throws Exception {
			SkillRequest skills = new SkillRequest(List.of(69, 70), "Rejected");
			HttpStatus result = this.restTemplate.postForEntity(rejectSkillsUrl, new HttpEntity<SkillRequest>(skills), String.class).getStatusCode();
			assertThat(result).isEqualTo(HttpStatus.OK);
		}
		
		@Test
		public void testRejectInvalidFormatSkills() throws Exception {
			List<Integer> skills = List.of(19, 20);
			HttpStatus result = this.restTemplate.postForEntity(rejectSkillsUrl, new HttpEntity<List<Integer>>(skills), String.class).getStatusCode();
			assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
		}
		
		@Test
		public void testRejectUnavailableSkills() throws Exception {
			SkillRequest skills = new SkillRequest(List.of(100), "Rejected");
			HttpStatus result = this.restTemplate.postForEntity(rejectSkillsUrl, new HttpEntity<SkillRequest>(skills), String.class).getStatusCode();
			assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
		}
}
