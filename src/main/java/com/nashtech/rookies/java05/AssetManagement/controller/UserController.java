package com.nashtech.rookies.java05.AssetManagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nashtech.rookies.java05.AssetManagement.dto.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.serviceimpl.UserServiceImpl;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponse createUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userServiceImpl.createUser(signUpRequest);
	}
}
