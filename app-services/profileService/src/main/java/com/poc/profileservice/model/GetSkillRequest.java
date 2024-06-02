package com.poc.profileservice.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSkillRequest {
	
	@NotBlank(message="Employee id should not be blank")
	private int employeeId;

}
