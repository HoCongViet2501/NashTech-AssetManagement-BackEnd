package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@Column(name = "user_id")
	@NotBlank(message = "ID.is.not.null")
	// Khai báo phương thức sinh id, sử dụng generator có tên generator_id
	@GeneratedValue(generator = "generator_id")
	// Khai báo generator có tên generator_id định nghĩa trong class StringGeneratorId
	@GenericGenerator(name = "generator_id", strategy = "core.generator.StringGeneratorId")
	private String id;

	@Column(name = "username")
	@NotBlank(message = "Username.is.not.null")
	@Size(min = 3, max = 20)
	private String username;

	@Column(name = "password")
	@NotBlank(message = "Password.is.not.null")
	@Size(min = 6, max = 20)
	private String password;

//	@Column(name = "role")
//	@Size(min = 1, max = 10)
//	private String role;
	
	@JoinColumn(name="role_id",nullable = false)
	@ManyToOne(optional = false)
	private Role role;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Information information;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UStatus status;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private Collection<Returning> returningUser;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "creator")
	private Collection<Returning> returningCreator;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private Collection<Assignment> assignmentUser;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "creator")
	private Collection<Assignment> assignmentCreator;
}
