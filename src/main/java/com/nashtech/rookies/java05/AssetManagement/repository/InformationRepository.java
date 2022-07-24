package com.nashtech.rookies.java05.AssetManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.Model.entity.Information;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long>{
//	Optional<Information> getByUsername(String username);
}
