package com.poc.profileservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
	
	APPROVED("approved"),
	REJECTED("rejected"),
	PENDING_APPROVAL("pending approval"),
	SUBMITTED("submitted");
	
	private final String value;

}
