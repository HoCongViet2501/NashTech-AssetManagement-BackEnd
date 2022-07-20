package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "returning")
public class Returning {
	@Id
	private Long returningId;
	
	private Date returnedDate;
	
	private String state;
	
	@OneToOne
	@JoinColumn(name = "returning_id", referencedColumnName = "assignment_id")
	private Assignment assignment;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "users_id")
	private User user;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "creator_id")
	private User creator;
}
