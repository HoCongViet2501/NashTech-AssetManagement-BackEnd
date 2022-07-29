package com.nashtech.rookies.java05.AssetManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByUserAndStatus(User user, boolean status);

    @Query(value = "select * from assignments a where a.asset_id = :id", nativeQuery = true)
    List<Assignment> findAssignmentByAsset(String id);

}
