package com.poc.managerService.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetManagerRequest {
	
	@NotBlank(message="Manager id should not be blank")
	int managerId;

}
