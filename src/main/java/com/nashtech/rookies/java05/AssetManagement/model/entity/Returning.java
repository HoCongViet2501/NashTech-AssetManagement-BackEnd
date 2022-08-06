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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "returnings")
public class Returning {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "returned_date")
	private Date returnedDate;
	
	private String state;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "assignment_id")
	private Assignment assignment;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "request_by")
	private User requestBy;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "accepted_by")
	private User acceptedBy;

	private boolean isDelete;
}
