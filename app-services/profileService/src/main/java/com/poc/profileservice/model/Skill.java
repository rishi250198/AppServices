package com.poc.profileservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="skill")
@Data
@NoArgsConstructor
public class Skill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	@JoinColumn(name="employee_id")
	private Employee employee;

	public Skill(String name, String family, String proficiency, String roleType, int experience,
			String currentHistoric, String approverComments, String experienceExposureText, String status,
			Employee employee) {
		super();
		this.name = name;
		this.family = family;
		this.proficiency = proficiency;
		this.roleType = roleType;
		this.experience = experience;
		this.currentHistoric = currentHistoric;
		this.approverComments = approverComments;
		this.experienceExposureText = experienceExposureText;
		this.status = status;
		this.employee = employee;
	}

}
