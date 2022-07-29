package com.nashtech.rookies.java05.AssetManagement.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "CreateNewUser.successfully")
	})
	@Operation(summary = "create new user")
	public UserResponse createUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.createUser(signUpRequest);
	}

	@GetMapping("/getAll/{location}")
	public List<UserDetailResponse> getAllUser(@PathVariable String location) {
		return this.userService.getAllUserSameLocation(location);
	}

	@GetMapping("/search/{location}/{content}")
	public List<UserDetailResponse> searchUser(@PathVariable String location, @PathVariable String content) {
		return this.userService.searchUser(content, location);
	}
	
	@PutMapping("/edit/{staffCode}")
	public UserResponse editUserInformation(@PathVariable String staffCode,@Valid @RequestBody SignupRequest signupRequest) {
		return userService.editUserInformation(staffCode, signupRequest);
	}
	
	@GetMapping("/getInformation/{staffCode}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "CreateNewUser.successfully")
	})
	@Operation(summary = "edit user information by staffCode")
	public List<UserDetailResponse> getUserInformation(@PathVariable String staffCode){
		return userService.getUserInformationById(staffCode);
	}
}
