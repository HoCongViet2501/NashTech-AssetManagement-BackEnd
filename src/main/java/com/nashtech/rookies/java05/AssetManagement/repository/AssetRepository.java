package com.nashtech.rookies.java05.AssetManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {

  @Query(value = "select a.* from information i inner join assets a on i.user_id = a.creator_id where i.location = :location", nativeQuery = true)
  public List<Asset> findAssetByLocation(String location);

  @Query(value = "select a.* from information i inner join assets a on i.user_id = a.creator_id where (a.name like %:content% or a.id like %:content%) and i.location = :location", nativeQuery = true)
  public List<Asset> searchAssetByNameOrCode(String content, String location);
}
