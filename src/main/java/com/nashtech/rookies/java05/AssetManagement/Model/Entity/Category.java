package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Category {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long categoryId;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
	private Set<Asset> asset;
}
