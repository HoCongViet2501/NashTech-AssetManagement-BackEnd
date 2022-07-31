package com.nashtech.rookies.java05.AssetManagement.model.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assignments")
public class Assignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "asset_id")
	private Asset asset;
	
	@ManyToOne(cascade = CascadeType.ALL,optional = false)
	@JoinColumn(name = "user_id" )
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL,optional = false)
	@JoinColumn(name = "creator_id")
	private User creator;
	
	@Column(name = "assigned_date")
	private Date assignedDate;
	
	private String note;
	
	private String state;

	private boolean status; // true: deleted
	
}
