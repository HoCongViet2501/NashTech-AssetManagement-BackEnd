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
@Table(name = "information")
@Entity
public class Information {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	@Size(min = 1, max = 15)
	private String firstname;

	@Column(name = "last_name")
	@Size(min = 1, max = 50)
	private String lastname;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_of_brith", columnDefinition = "DATE")
	private Date dateOfBirth;

	@Column(name = "gender")
	private boolean gender;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "joined_date", columnDefinition = "DATE")
	private Date joineddate;
	
	@JoinColumn(name = "user_id")
	@OneToOne
	private User user;
}
