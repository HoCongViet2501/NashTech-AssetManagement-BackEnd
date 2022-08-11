package com.nashtech.rookies.java05.AssetManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Operation(summary = "Create new user")
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "CreateNewUser.successfully")
	})
	public UserResponse createUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.createUser(signUpRequest);
	}

	@GetMapping("/getAll")
	@Operation(summary = "Get all user same location")
	public List<UserDetailResponse> getAllUser() {
		return this.userService.getAllUserSameLocation();
	}

	@GetMapping("/search/{content}")
	@Operation(summary = "Search user by content")
	public List<UserDetailResponse> searchUser(@PathVariable String content) {
		return this.userService.searchUser(content);
	}

	@GetMapping("/check/{staffCode}")
	@Operation(summary = "Check user can disable or not")
	public ResponseEntity<Boolean> checkUserAvailableToDelete(@PathVariable String staffCode) {
		return ResponseEntity.ok().body(this.userService.checkUserIsAvailable(staffCode));
	}

	@PatchMapping("/disable/{staffCode}")
	@Operation(summary = "disable user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "user.is.disabled.successfully"),
			@ApiResponse(responseCode = "404", description = "user.not.found"),
			@ApiResponse(responseCode = "403", description = "access.denied")

	})
	public ResponseEntity<Object> disableUser(@PathVariable String staffCode) {
		return this.userService.disableUser(staffCode);
	}

	@PutMapping("/edit/{staffCode}")
	public UserResponse editUserInformation(@PathVariable String staffCode,
			@Valid @RequestBody SignupRequest signupRequest) {
		return userService.editUserInformation(staffCode, signupRequest);
	}

	@GetMapping("/getInformation/{staffCode}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "CreateNewUser.successfully")
	})
	@Operation(summary = "edit user information by staffCode")
	public List<UserDetailResponse> getUserInformation(@PathVariable String staffCode) {
		return userService.getUserInformationById(staffCode);
	}
}
