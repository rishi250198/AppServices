package com.poc.authservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private int id;
	private String firstName;
	private String lastName;
	private String role;
	private int managerId;
	private List<Skill> skills;
}
