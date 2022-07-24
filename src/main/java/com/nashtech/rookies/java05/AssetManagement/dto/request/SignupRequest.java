package com.nashtech.rookies.java05.AssetManagement.dto.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SignupRequest {

	@NotBlank(message = "FirstName must not empty")
	private String firstName;

	@NotBlank(message = "LastName must not empty")
	private String lastName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	private boolean gender;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date joinedDate;

	private Long role;
	
	private String location;


	@Override
	public String toString() {
		return "SignupRequest{" +
				"firstname='" + firstName + '\'' +
				", lastname='" + lastName + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", gender=" + gender +
				", joinedDate=" + joinedDate +
				", role=" + role +
				'}';
	}
}
