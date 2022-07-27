package com.nashtech.rookies.java05.AssetManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;



@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {
	@Query(value = "select i.location from information i where i.user_id ="
			+ " (select id from users where username = :userName)", nativeQuery = true)
    public String getLocationByUserName(String userName);

    public List<Information> findByLocation(String location);

    @Query(value = "select  count(i.id) from information i  where i.location = :location", nativeQuery = true)
    public int findTotalUserSameLocation(@Param("location") String location);

    @Query(value = "select * from information i where i.location = :location limit 50 offset :offset ", nativeQuery = true)
    public List<Information> findAllUserSameLocation(@Param("location") String location, @Param("offset") int raw);

    @Query(value = "select * from information i where (i.first_name like %:content% or i.last_name like %:content%  or  i.user_id like %:content%) and i.location  = :location", nativeQuery = true)
    public List<Information> searchUser(@Param("content") String content, @Param("location") String location);
    
    @Query(value = "select i.id from Information i where i.user_id = :userId", nativeQuery = true)
    public Long getInformationIbByUserId(String userId);

}
