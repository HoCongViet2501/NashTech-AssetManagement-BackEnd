package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
	@Id
	@NotBlank(message = "ID.is.not.null")
	@Column(name = "users_id")
	private String usersId;

	@NotBlank(message = "Username.is.not.null")
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank(message = "Password.is.not.null")
	@Size(min = 6, max = 20)
	private String password;

	@ManyToOne
	@JoinColumn(name="role_id",nullable = false)
	private Role role;

	@Enumerated(EnumType.STRING)
	private UStatus status;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "users")
	private Set<Returning> returningUser;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "creator")
	private Set<Returning> returningCreator;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "users")
	private Set<Assignment> assignmentUser;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "creator")
	private Set<Assignment> assignmentCreator;

	@OneToOne(mappedBy = "users")
	private Information information;
}
