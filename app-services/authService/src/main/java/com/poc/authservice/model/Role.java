package com.poc.authservice.model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

	ADMIN("admin"),
	MANAGER("manager"),
	ASSOCIATE("associate");
	
	private final String value;
	
}

