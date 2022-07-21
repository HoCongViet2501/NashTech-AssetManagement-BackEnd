package com.nashtech.rookies.java05.AssetManagement.response;

import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Role;
import com.nashtech.rookies.java05.AssetManagement.Model.enums.UStatus;

import lombok.Data;

@Data
public class UserResponse {
	
	private String staffCode;
	
	private String username;
	
	private String firstname;

	@Size(min = 2, max = 50)
	private String lastname;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	private boolean gender;

	@Temporal(TemporalType.DATE)
	private Date joinedDate;
	
	private Role roles;
	
	@Enumerated(EnumType.STRING)
    private UStatus status;
}
