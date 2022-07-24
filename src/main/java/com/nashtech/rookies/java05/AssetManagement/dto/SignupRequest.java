package com.nashtech.rookies.java05.AssetManagement.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nashtech.rookies.java05.AssetManagement.Model.entity.Role;
import com.nashtech.rookies.java05.AssetManagement.Model.entity.Users;
import com.nashtech.rookies.java05.AssetManagement.Model.enums.UStatus;

import lombok.Data;

@Data
public class SignupRequest {

	@NotBlank(message = "Firstname must not empty")
//	@Size(min = 2, max = 50, message = "Firstname must be between {min} and {max}")
	private String firstname;

	@NotBlank(message = "Lastname must not empty")
//	@Size(min = 2, max = 50, message = "Lastname must be between {min} and {max}")
	private String lastname;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	private boolean gender;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date joinedDate;

	private Long role;
	
	private String location;

//	private String status;

//	private Users users;


	@Override
	public String toString() {
		return "SignupRequest{" +
				"firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", gender=" + gender +
				", joinedDate=" + joinedDate +
				", role=" + role +
				'}';
	}
}
