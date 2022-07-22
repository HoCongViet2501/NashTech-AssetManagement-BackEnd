package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "information")
public class Information {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "date_birth")
	private Date dateOfBirth;
	
	private boolean gender;
	
	@Column(name = "joined_date")
	@CreationTimestamp
	private Date joinedDate;

	@Column(name = "location")
	private String location;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
}
