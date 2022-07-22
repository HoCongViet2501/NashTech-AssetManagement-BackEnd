package com.nashtech.rookies.java05.AssetManagement.Model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Information;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {
    public List<Information> findByLocation(String location);

}