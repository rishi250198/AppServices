package com.poc.managerService.model;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetManagerResponse {
	
	@Id
	private int id;
	@NotBlank(message="Skill Name should not be empty")
	private String name;
	@NotBlank(message="Skill Family should not be empty")
	private String family;
	@NotBlank(message="Skill Proficiency should not be empty")
	private String proficiency;
	@NotBlank(message="Role Type should not be empty")
	private String roleType;
	private int experience;
	@NotBlank(message="Skill Current/Historic should not be empty")
	private String currentHistoric;
	private String approverComments;
	private String experienceExposureText;
	private String status;
	@NotBlank(message="Employee id cannot be empty")
	private int employeeId;

}
