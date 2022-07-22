package com.nashtech.rookies.java05.AssetManagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.java05.AssetManagement.dto.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.serviceimpl.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	@PostMapping("/register")
	public UserResponse createUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userServiceImpl.createUser(signUpRequest);
	}
}
