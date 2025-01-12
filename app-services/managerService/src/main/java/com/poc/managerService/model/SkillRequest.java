package com.poc.managerService.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequest {
	
	@NotBlank(message="Skill ids should not be blank")
	List<Integer> skillIds;
	String approverComments;

}
