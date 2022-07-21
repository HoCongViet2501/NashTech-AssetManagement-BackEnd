package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assignmentId;

	@ManyToOne
	@JoinColumn(name = "asset_id")
	private Asset asset;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private Users users;

	@ManyToOne(optional = false)
	@JoinColumn(name = "creator_id")
	private Users creator;

	private Date assignDate;

	private String note;

	private String state;

	@OneToOne(mappedBy = "assignment", cascade = CascadeType.ALL)
	private Returning returning;
}
