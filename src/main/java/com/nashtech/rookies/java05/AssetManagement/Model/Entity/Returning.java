package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "returnings")
public class Returning {
	@Id
	@NotBlank(message = "ID.is.not.null")
	private Long id;
	
	@Id
	@Column(name = "returned_date")
	private Date returnedDate;
	
	@Column
	private String state;
	
	
	@OneToOne
	@JoinColumn(name = "assignment_id",referencedColumnName = "id")
	private Assignment assignment;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User creator;

}
