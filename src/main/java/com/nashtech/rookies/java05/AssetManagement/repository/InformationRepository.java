package com.nashtech.rookies.java05.AssetManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long>{
}