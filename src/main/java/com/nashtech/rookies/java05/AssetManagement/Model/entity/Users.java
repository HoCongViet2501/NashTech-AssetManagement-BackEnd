package com.nashtech.rookies.java05.AssetManagement.Model.entity;

import java.util.Collection;
import java.util.Set;

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

import com.nashtech.rookies.java05.AssetManagement.Model.enums.UStatus;

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
public class Users {
	@Id
	@NotBlank(message = "ID.is.not.null")
	@GeneratedValue(generator = "my_generator")
	@GenericGenerator(name = "my_generator", 
	strategy = "com.nashtech.rookies.java05.AssetManagement.generator.MyGenerator")
	private String userId;

	@NotBlank(message = "Username.is.not.null")
//	@Size(min = 3, max = 20)
	private String username;

	@NotBlank(message = "Password.is.not.null")
//	@Size(min = 6, max = 20)
	private String password;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@Enumerated(EnumType.STRING)
	private UStatus status;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
	private Set<Returning> returningUser;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
	private Set<Returning> returningCreator;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
	private Set<Assignment> assignmentUser;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
	private Set<Assignment> assignmentCreator;

	@OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
	private Information information;

	public Users(String username, String encode) {
		this.username = username;
		this.password = encode;
	}
}
