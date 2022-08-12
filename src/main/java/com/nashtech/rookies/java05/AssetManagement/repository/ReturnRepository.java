package com.nashtech.rookies.java05.AssetManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Returning;

@Repository
public interface ReturnRepository extends JpaRepository<Returning, Long> {
    @Query(value = "SELECT r.id, r.is_delete, r.returned_date , r.state , r.accepted_by , r.assignment_id , r.request_by "
    		+ "from information i inner join returnings r on r.request_by = i.user_id  "
    		+ "where i.location = :location and r.is_delete = false ", nativeQuery = true)
    public List<Returning> getAllReturning(@Param("location") String location);

    @Query(value = "SELECT r.id, r.is_delete, r.returned_date , r.state , r.accepted_by , r.assignment_id , r.request_by from information i inner join returnings r on r.request_by=i.user_id inner join  assignments a inner join assets a2 on a.asset_id = a2.id on r.assignment_id  = a.id  inner join users u on u.id  = r.request_by where i.location = :location and r.is_delete = false and a2.name like %:content% or a2.id like %:content% or u.username like %:content%", nativeQuery = true)
    public List<Returning> search(@Param("location") String location, @Param("content") String content);
    
}
