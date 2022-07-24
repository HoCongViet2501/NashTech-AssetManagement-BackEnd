package com.nashtech.rookies.java05.AssetManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long>{
    @Query(value = "select i.location from information i where i.user_id ="
			+ " (select id from users where username = :userName)", nativeQuery = true)
    public String getLocationByUserName(String userName);
}
