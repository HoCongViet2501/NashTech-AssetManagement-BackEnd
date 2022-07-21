package com.nashtech.rookies.java05.AssetManagement.Model.DTO;

import java.util.Date;

import javax.validation.constraints.Size;

import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Role;
import com.nashtech.rookies.java05.AssetManagement.Model.enums.UStatus;

import lombok.Data;

@Data
public class SignupRequest {

	private String usersId;

	private String username;

	private String password;

	private String firstname;

	@Size(min = 2, max = 50)
	private String lastname;

	private Date dateOfBirth;

	private boolean gender;

	private Date joinedDate;

	private String location;
	
	private String role;

	private UStatus status;

}
