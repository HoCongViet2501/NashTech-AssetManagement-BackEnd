package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
public class Returning {
	@Id
	@NotBlank(message = "ID.is.not.null")
	private Long returningId;

	private Date returnedDate;

	private String state;

	@OneToOne
	@JoinColumn(name = "returning_id",referencedColumnName = "assignment_id")
	private Assignment assignment;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "users_id")
	private Users users;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "creator_id")
	private Users creator;
}
