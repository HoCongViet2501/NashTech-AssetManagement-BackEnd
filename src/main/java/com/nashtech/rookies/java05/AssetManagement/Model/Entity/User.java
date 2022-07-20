package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nashtech.rookies.java05.AssetManagement.Model.enums.UserStatus;
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
	@Column(name = "users_id")
	private String usersId;
	
	@Size(min = 3, max = 20)
	private String username;
	
	@Size(min = 6, max = 20)
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Returning> returningUser;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
	private Set<Returning> returningCreator;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Assignment> assignmentUser;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
	private Set<Assignment> assignmentCreator;
	
	@OneToOne(mappedBy = "users")
	private Information information;
}
