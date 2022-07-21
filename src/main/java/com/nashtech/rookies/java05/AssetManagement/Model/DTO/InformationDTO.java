package com.nashtech.rookies.java05.AssetManagement.Model.DTO;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Users;

import lombok.Data;

@Data
public class InformationDTO {

	private Long informationId;

	@NotBlank(message = "Firstname must not empty")
	@Size(min = 2, max = 50, message = "Firstname must be between {min} and {max}")
	private String firstname;

	@NotBlank(message = "Firstname must not empty")
	@Size(min = 2, max = 50, message = "Firstname must be between {min} and {max}")
	private String lastname;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//	@NotNull(message = "Date of birth must not empty")
	private Date dateOfBirth;

	@NotNull(message = "null")
	private boolean gender;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//	@NotNull(message = "Join date must not empty")
	private Date joinedDate;

	@NotBlank(message = "blank")
	private String location;

	private Users users;

}
