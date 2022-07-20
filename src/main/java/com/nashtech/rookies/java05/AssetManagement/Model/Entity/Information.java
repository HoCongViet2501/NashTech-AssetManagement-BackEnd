package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

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
	private Long informationId;
	
	@Size(min = 1, max = 15)
	private String firstname;
	
	@Size(min = 1, max = 50)
	private String lastname;
	
	private Date dateOfBirth;
	
	private boolean gender;
	
	private Date joinedDate;
	
	@OneToOne
	@JoinColumn(name = "users_id", referencedColumnName = "users_id")
	private User users;
}
