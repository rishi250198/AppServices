package com.poc.managerService.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
	
	APPROVED("approved"),
	REJECTED("rejected"),
	PENDING_APPROVAL("pending approval");
	
	private final String value;

}



	
	
