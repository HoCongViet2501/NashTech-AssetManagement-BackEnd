package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "employee", schema = "final")
public class Employee {
	@Id
	@Column(name = "id")
	@NotBlank(message = "ID is not null")
	private String id;

	@Column(name = "username")
	@NotBlank(message = "Username is not null")
	@Size(min = 3, max = 20)
	private String username;

	@Column(name = "password")
	@Size(min = 6, max = 20)
	private String password;

	@Column(name = "firstname")
	@Size(min = 1, max = 15)
	private String firstname;

	@Column(name = "lastname")
	@Size(min = 1, max = 50)
	private String lastname;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dob", columnDefinition="DATE")
	private Date dob;

	@Column(name = "gender")
	private boolean gender;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "joineddate", columnDefinition="DATE")
	private Date joineddate;

	@Column(name = "role")
	@Size(min = 1, max = 10)
	private String role;

	@Column(name = "status")
	private int status;
}
