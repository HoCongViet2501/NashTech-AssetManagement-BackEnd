package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "returnings")
public class Returning {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "returned_date")
	private Date returnedDate;
	
	private String state;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "assignment_id")
	private Assignment assignment;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private User creator;
}
