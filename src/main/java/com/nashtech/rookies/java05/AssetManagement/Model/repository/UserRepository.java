package com.nashtech.rookies.java05.AssetManagement.Model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.java05.AssetManagement.Model.Entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
