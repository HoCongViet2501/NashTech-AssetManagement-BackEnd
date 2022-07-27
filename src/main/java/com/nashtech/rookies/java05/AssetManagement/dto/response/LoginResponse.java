package com.nashtech.rookies.java05.AssetManagement.dto.response;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Role;
import com.nashtech.rookies.java05.AssetManagement.model.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
	
	
	private String token;

	private String userId;

	private String username;

	private Role role;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	private String location;
}
