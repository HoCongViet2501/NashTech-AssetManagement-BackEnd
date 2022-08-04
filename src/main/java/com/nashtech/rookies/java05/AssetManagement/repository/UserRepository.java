package com.nashtech.rookies.java05.AssetManagement.repository;

import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.model.enums.UserStatus;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUserName(String userName);

	Optional<User> findUserById(String id);

	@Query(value = "select u.* from users u where u.username = :username and u.status = :status", nativeQuery = true)
	Optional<User> findByUserNameAndStatus(@Param("username") String username, @Param("status") String status);
}
