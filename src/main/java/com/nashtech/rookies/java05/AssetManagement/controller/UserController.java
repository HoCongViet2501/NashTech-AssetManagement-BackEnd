package com.nashtech.rookies.java05.AssetManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AllUserResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;


@RestController
// @PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponse createUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.createUser(signUpRequest);
	}

	@GetMapping("/getAll/{location}")
    public List<UserDetailResponse> getAllUserSameLocation(@PathVariable String location) {
        return userService.getAllUserSameLocation(location);
    }

	@GetMapping("/getAll/{location}/{raw}")
	public AllUserResponse getAllUser(@PathVariable String location, @PathVariable int raw) {
		return this.userService.getAllUserResponse(location, raw);
	}

	@GetMapping("/search/{location}/{content}")
	public AllUserResponse searchUser(@PathVariable String location, @PathVariable String content) {
		return this.userService.searchUser(content, location);
	}
}
