package com.nashtech.rookies.java05.AssetManagement.repository;

import java.util.List;
import java.util.Optional;

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

	@Query(value = "select i.id, i.date_birth , i.first_name, i.gender, i.last_name, i.joined_date , i.user_id, i.location  from information i inner join users u  on u.id = i.user_id where i.location = :location and u.status != 'INACTIVE'", nativeQuery = true)
	public List<Information> findUserByLocationAndNotInactive(@Param("location") String location);

	@Query(value = "select  count(i.id) from information i  where i.location = :location", nativeQuery = true)
	public int findTotalUserSameLocation(@Param("location") String location);

	@Query(value = "select * from information i where i.location = :location limit 50 offset :offset ", nativeQuery = true)
	public List<Information> findAllUserSameLocation(@Param("location") String location, @Param("offset") int raw);

	@Query(value = "select * from information i where (i.first_name like %:content% or i.last_name like %:content%  or  i.user_id like %:content%) and i.location  = :location", nativeQuery = true)
	public List<Information> searchUser(@Param("content") String content, @Param("location") String location);

	@Query(value = "select i.id from Information i where i.user_id = :userId", nativeQuery = true)
	public Long getInformationIdByUserId(String userId);

	public List<Information> findInformationByUserId(String userId);

	Optional<Information> findByUserId(String userId);

	@Query(value = "select * from information i where i.user_id = "
			+ "(select u.id  from users u where u.username = :username )",nativeQuery = true)
	Optional<Information> findByUsername(@Param("username")String username);
	
	
	@Query(value = "select i.id, i.date_birth , i.first_name, i.gender, i.last_name, i.joined_date , i.user_id, i.location  from information i "
			+ "inner join users u  on u.id = i.user_id "
			+ "where i.location = :location and u.status = 'ACTIVE'", nativeQuery = true)
	public List<Information> findUserByLocationAndStatus(@Param("location") String location);
	
	@Query(value = "select * from information i "
			+ "join users u on u.id = i.user_id "
			+ "where (lower(i.first_name) like lower(concat('%', :content,'%')) or lower(i.last_name) like lower(concat('%', :content,'%'))  or  lower(i.user_id) like lower(concat('%', :content,'%'))) "
			+ "and i.location  = :location and u.status = 'ACTIVE'", nativeQuery = true)
	public List<Information> searchUserAndStatus(@Param("content") String content, @Param("location") String location);
	
	
}
