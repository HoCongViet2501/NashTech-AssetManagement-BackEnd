package com.nashtech.rookies.java05.AssetManagement.model.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assets")
@Entity
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

	@JsonIgnore
	@OneToMany(mappedBy = "asset",cascade = CascadeType.ALL)
	private Set<Assignment> assignment;
}
