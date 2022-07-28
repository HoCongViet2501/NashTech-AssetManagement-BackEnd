package com.nashtech.rookies.java05.AssetManagement.repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String>{
    @Query(value = "SELECT * FROM assets a WHERE a.category_id LIKE %:category ORDER BY  a.id desc", nativeQuery = true)
    List<Asset> findByOrderByIdDesc(String category);
 }
