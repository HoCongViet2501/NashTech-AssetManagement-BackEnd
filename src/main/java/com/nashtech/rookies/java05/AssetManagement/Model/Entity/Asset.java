package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

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
	
	@Column(name = "installed_date")
	private Date installedDate;
	
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "asset")
	private Collection<Assignment> assignment;
}
