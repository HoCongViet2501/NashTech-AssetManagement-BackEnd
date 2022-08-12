package com.nashtech.rookies.java05.AssetManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUserName(String userName);

	Optional<User> findUserById(String id);

	@Query(value = "select u.* from users u where u.id = :id and u.status = :status", nativeQuery = true)
	Optional<User> findByUserNameAndStatus(@Param("id") String id, @Param("status") String status);
	
}
