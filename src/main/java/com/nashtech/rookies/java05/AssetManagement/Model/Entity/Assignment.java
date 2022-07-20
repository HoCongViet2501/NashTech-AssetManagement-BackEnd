package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "assignments")
public class Assignment {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "asset_id")
	private Asset assets;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User creator;

	@Column(name = "assignment_date")
	private Date assignDate;

	@Column(name = "note")
	private String note;
	@Column(name = "state")
	private String state;

	@OneToOne(mappedBy = "assignment", cascade = CascadeType.ALL)
	private Returning returning;

}
