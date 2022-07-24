package com.nashtech.rookies.java05.AssetManagement.Model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.Model.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
