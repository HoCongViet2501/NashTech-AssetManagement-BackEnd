package com.nashtech.rookies.java05.AssetManagement.dto.response;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.nashtech.rookies.java05.AssetManagement.Model.entity.Role;
import com.nashtech.rookies.java05.AssetManagement.Model.enums.UserStatus;

import lombok.Data;

@Data
public class UserResponse {

	private String userId;

	private String username;

	private Role role;

	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	InformationResponse informationResponse;
}
