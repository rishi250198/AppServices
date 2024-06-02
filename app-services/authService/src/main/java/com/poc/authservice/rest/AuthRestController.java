package com.poc.authservice.rest;

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

import com.poc.authservice.model.Employee;
import com.poc.authservice.model.LoginRequest;
import com.poc.authservice.model.LoginResponse;
import com.poc.authservice.model.ManagerRequest;
import com.poc.authservice.service.AuthService;
import com.poc.authservice.service.ManagerService;

@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/auth")
public class AuthRestController {
	@Autowired
	private AuthService authService;
	
	@Autowired
	private ManagerService managerService;
	
	private static final Logger logger= LogManager.getLogger(AuthRestController.class);
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginEmployee( @RequestBody LoginRequest credentials) throws Exception {
		logger.debug(credentials);
		
		String email = credentials.getEmail().trim().toLowerCase();
		String password = credentials.getPassword().trim();
		
		Employee emp = authService.loginService(email, password);
		
		LoginResponse employeeResponse = new LoginResponse(emp.getId(),emp.getFirstName(),emp.getLastName(),emp.getRole(),emp.getManagerId(),emp.getSkills());
		
		logger.debug("Found employee with Email, Password : "+email+", "+password);
		
		return new ResponseEntity<>(employeeResponse,HttpStatus.OK);
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<String> registerEmployee( @RequestBody Employee employee) throws Exception {
		
		Employee emp = authService.registrationService(employee);
		
		logger.debug("New employee created");
		logger.debug(emp);
		
		return new ResponseEntity<>("Employee created succesfully!!",HttpStatus.CREATED);
	}
	
	@PostMapping("/managers")
	public ResponseEntity<List<Employee>> getAllManagers() throws Exception {
		List<Employee> managers = managerService.getManagers();
		
		logger.debug("Fetched all managers");
		logger.debug(managers);
		
		return new ResponseEntity<>(managers, HttpStatus.OK);
	}
	
	@PostMapping("/update/manager")
	public ResponseEntity<Employee> updateManagerId( @RequestBody ManagerRequest managerRequest) throws Exception {
		String managerEmail = managerRequest.getManagerEmail();
		int employeeId = managerRequest.getEmployeeId();
		
		Employee employee = managerService.updateManager(managerEmail, employeeId);
		
		logger.debug("Updated manager id");
		logger.debug(employee);
		
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String test() {
		return "done";
	}
	
	
}
