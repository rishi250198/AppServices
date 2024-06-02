package com.poc.authservice.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import com.poc.authservice.model.Employee;
import com.poc.authservice.model.LoginRequest;
import com.poc.authservice.model.ManagerRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthRestControllerTest {
	
	@Value(value="${local.server.port}")
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value(value="${api.test.url}")
	private String testUrl;
	
	@Value(value="${api.login.url}")
	private String loginUrl;
	
	@Value(value="${api.register.url}")
	private String registerUrl;
	
	@Value(value="${api.managers.url}")
	private String managersUrl;
	
	@Value(value="${api.updatemanager.url}")
	private String updateManagerUrl;

	
	//Test for the Test API in AuthRestController
	@Test
	public void testShouldReturnDefaultMessage() throws Exception{
		assertThat(this.restTemplate.getForObject(testUrl,String.class)).contains("done");
	}
	
	//Tests for the Login Employee API in AuthRestController
	@Test
	public void testShouldLoginEmployee() throws Exception{
		LoginRequest loginCredentials = new LoginRequest("rishi.admi@gmail.com","admin@1234");
		HttpStatus result = this.restTemplate.postForEntity(loginUrl,new HttpEntity<LoginRequest>(loginCredentials), Employee.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testNotExistingEmailLoginEmployee() throws Exception{
		LoginRequest loginCredentials = new LoginRequest("tets@hcl.com","Admin1029!@");
		HttpStatus result = this.restTemplate.postForEntity(loginUrl,new HttpEntity<LoginRequest>(loginCredentials), Employee.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testWrongPasswordLoginEmployee() throws Exception{
		LoginRequest loginCredentials = new LoginRequest("admin@hcl.com","Admin102!@");
		HttpStatus result = this.restTemplate.postForEntity(loginUrl,new HttpEntity<LoginRequest>(loginCredentials), Employee.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testEmptyPasswordLoginEmployee() throws Exception{
		LoginRequest loginCredentials = new LoginRequest("admin@hcl.com","");
		HttpStatus result = this.restTemplate.postForEntity(loginUrl,new HttpEntity<LoginRequest>(loginCredentials), Employee.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testEmptyEmailLoginEmployee() throws Exception{
		LoginRequest loginCredentials = new LoginRequest("","Admin1029!@");
		HttpStatus result = this.restTemplate.postForEntity(loginUrl,new HttpEntity<LoginRequest>(loginCredentials), Employee.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	//Tests for the Register Employee API in AuthRestController
	@Test
	@Order(1)	
	public void testShouldRegisterEmployee() throws Exception{
		Employee employee= new Employee("authTest@gmail.com","test","test","Auth1029!@","male","manager",0, null);
		HttpStatus result =this.restTemplate.postForEntity(registerUrl,new HttpEntity<Employee>(employee),String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void testWrongEmailFormatRegisterEmployee() throws Exception{
		Employee employee= new Employee("authTest1gmail.com","test","test","Auth1029!@","male","manager",0, null);
		HttpStatus result =this.restTemplate.postForEntity(registerUrl,new HttpEntity<Employee>(employee),String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testWrongPasswordLengthRegisterEmployee() throws Exception{
		Employee employee= new Employee("authTest1@gmail.com","test","test","Auth","male","manager",0, null);
		HttpStatus result =this.restTemplate.postForEntity(registerUrl,new HttpEntity<Employee>(employee),String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testEmptyPasswordRegisterEmployee() throws Exception{
		Employee employee= new Employee("authTest1@gmail.com","test","test","","male","manager",0, null);
		HttpStatus result =this.restTemplate.postForEntity(registerUrl,new HttpEntity<Employee>(employee),String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testInvalidRoleRegisterEmployee() throws Exception{
		Employee employee= new Employee("authTest@gmail.com","test","test","Auth1029!@","male","testrole",0, null);
		HttpStatus result =this.restTemplate.postForEntity(registerUrl,new HttpEntity<Employee>(employee),String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testEmployeeExistsRegisterEmployee() throws Exception{
		Employee employee= new Employee("authTest@gmail.com","test","test","Auth1029!@","male","manager",0, null);
		HttpStatus result =this.restTemplate.postForEntity(registerUrl,new HttpEntity<Employee>(employee),String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	//Test for the Get Managers API in AuthRestController
	@Test
	public void testGetManagers() throws Exception {
		HttpStatus result = this.restTemplate.postForEntity(managersUrl, null, String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	
	//Tests for the Update Manager API in AuthRestController
	@Test
	public void testShouldUpdateManager() throws Exception {
		ManagerRequest managerRequest = new ManagerRequest("authtest@gmail.com", 56);
		HttpStatus result = this.restTemplate.postForEntity(updateManagerUrl, new HttpEntity<ManagerRequest>(managerRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testInvalidRoleUpdateManager() throws Exception {
		ManagerRequest managerRequest = new ManagerRequest("rishi.admin@gmail.com", 55);
		HttpStatus result = this.restTemplate.postForEntity(updateManagerUrl, new HttpEntity<ManagerRequest>(managerRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testInvalidEmployeeUpdateManager() throws Exception {
		ManagerRequest managerRequest = new ManagerRequest("rishi.manager@gmail.com", 100);
		HttpStatus result = this.restTemplate.postForEntity(updateManagerUrl, new HttpEntity<ManagerRequest>(managerRequest), String.class).getStatusCode();
		assertThat(result).isEqualTo(HttpStatus.NOT_FOUND);
	}
}

