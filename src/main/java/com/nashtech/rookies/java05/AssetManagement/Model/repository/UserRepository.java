package com.nashtech.rookies.java05.AssetManagement.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nashtech.rookies.java05.AssetManagement.model.entity.User;


public interface UserRepository extends JpaRepository<User, String> {
    public List<User> findByLocation(String location);  // Nhu
    @Query()
    public Optional<User> searchUser();
}
