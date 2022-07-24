package com.nashtech.rookies.java05.AssetManagement.model.entity;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nashtech.rookies.java05.AssetManagement.model.enums.UserStatus;

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
	@Column(name = "id")
	@GeneratedValue(generator = "my_generator")
	@GenericGenerator(name = "my_generator", strategy = "com.nashtech.rookies.java05.AssetManagement.generator.MyGenerator")
	private String id;

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String passWord;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Returning> returningUser;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
	private Set<Returning> returningCreator;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Assignment> assignmentUser;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
	private Set<Assignment> assignmentCreator;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Information information;
}
