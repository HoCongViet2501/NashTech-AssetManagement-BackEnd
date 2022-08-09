package com.nashtech.rookies.java05.AssetManagement.model.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "assets")
public class Asset {
	
	@Id
	private String id;
	
	private String name;
	
	private String specification;
	
	@Temporal(TemporalType.DATE)
    @Column(nullable = false)
	private Date installedDate;
	
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creator;

	@JsonIgnore
	@OneToMany(mappedBy = "asset",cascade = CascadeType.ALL)
	private Set<Assignment> assignment;
}
