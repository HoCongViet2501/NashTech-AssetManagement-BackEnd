package com.nashtech.rookies.java05.AssetManagement.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.nashtech.rookies.java05.AssetManagement.Model.entity.Role;
import com.nashtech.rookies.java05.AssetManagement.Model.enums.UStatus;

import lombok.Data;

@Data
public class UserResponse {

	private String userId;

	private String username;

	private Role role;

	@Enumerated(EnumType.STRING)
	private UStatus status;
	
	InformationResponse informationResponse;
}
