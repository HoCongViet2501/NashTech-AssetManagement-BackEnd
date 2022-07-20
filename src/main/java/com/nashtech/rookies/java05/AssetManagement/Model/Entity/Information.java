package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
	@JoinColumn(name = "users_id",referencedColumnName = "users_id")
	private Users users;
}
