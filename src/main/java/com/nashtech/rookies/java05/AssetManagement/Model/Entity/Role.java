package com.nashtech.rookies.java05.AssetManagement.Model.Entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private URole name;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<Users> users;
}
