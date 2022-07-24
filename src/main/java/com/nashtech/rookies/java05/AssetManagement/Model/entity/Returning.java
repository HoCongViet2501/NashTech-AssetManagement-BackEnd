package com.nashtech.rookies.java05.AssetManagement.Model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long returningId;

	@Temporal(TemporalType.DATE)
    @Column(nullable = false)
	private Date returnedDate;

	private String state;

	@OneToOne
	@JoinColumn(name = "returning_id")
	private Assignment assignment;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private Users users;

	@ManyToOne(optional = false)
	@JoinColumn(name = "creator_id")
	private Users creator;
}
