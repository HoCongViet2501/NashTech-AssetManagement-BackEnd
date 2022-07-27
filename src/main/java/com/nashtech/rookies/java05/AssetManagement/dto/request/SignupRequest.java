package com.nashtech.rookies.java05.AssetManagement.dto.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SignupRequest {

	@NotBlank(message = "FirstName must not empty")
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
	private String firstName;

	@NotBlank(message = "LastName must not empty")
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
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
		return "SignupRequest{" + "firstname='" + firstName + '\'' + ", lastname='" + lastName + '\'' + ", dateOfBirth="
				+ dateOfBirth + ", gender=" + gender + ", joinedDate=" + joinedDate + ", role=" + role + '}';
	}
}
