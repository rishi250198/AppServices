package com.poc.adminservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="template")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message="From address should not be empty")
	private String fromAddress;
	@NotBlank(message="To address should not be empty")
	private String toAddress;
	@NotBlank(message="CC address cannot be blank")
	private String cc;
	@NotBlank(message="Subject should not be empty")
	private String subject;
	private String bodyContent;
	private int createdBy;
	private String status;

}
