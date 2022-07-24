package com.nashtech.rookies.java05.AssetManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.Model.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
	Optional<Users> findByUsername(String username);
}
