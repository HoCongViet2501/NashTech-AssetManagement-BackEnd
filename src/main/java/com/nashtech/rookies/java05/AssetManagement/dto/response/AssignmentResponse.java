package com.nashtech.rookies.java05.AssetManagement.dto.response;

import java.util.Date;

import lombok.Data;

@Data
public class AssignmentResponse {
	private Long id;

	private AssetResponse assetResponse;

	private UserResponse user;

	private UserResponse createUser;

	private Date assignedDate;

	private String state;

	private String note;

	private boolean status;
	
//	private boolean hasReturning;
}
