package com.poc.authservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employee")
@Data
@NoArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message="Email should not be empty")
	@Email
	private String email;
	@NotBlank(message="First Name should not be empty")
	@NotBlank
	private String firstName;
	@NotBlank(message="Last Name should not be empty")
	@NotBlank
	private String lastName;
	@NotBlank(message="Password should not be empty")
	@NotBlank
	private String password;
	@NotBlank(message="Gender should not be empty")
	@NotBlank
	private String gender;
	private String role;
	private int managerId;
	
	@JsonManagedReference
	@Transient
	@OneToMany(mappedBy = "employee",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	private List<Skill> skills;
	
	public Employee(String email, String firstName, String lastName, String password, String gender, String role,
			int managerId, List<Skill> skills) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.gender = gender;
		this.role = role;
		this.managerId = managerId;
		this.skills = skills;
	}
}
