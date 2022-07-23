package com.nashtech.rookies.java05.AssetManagement.controller;

import com.nashtech.rookies.java05.AssetManagement.Model.Entity.User;
import com.nashtech.rookies.java05.AssetManagement.dto.request.LoginRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.LoginResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static com.nashtech.rookies.java05.AssetManagement.mapper.MappingData.mapToResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthenticationServiceImpl authenticationService;
	private final PasswordEncoder passwordEncoder;
	
	public AuthController(AuthenticationServiceImpl authenticationService, PasswordEncoder passwordEncoder) {
		this.authenticationService = authenticationService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping("/login")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "login.successfully"),
			@ApiResponse(responseCode = "302", description = "username.or.password.is.incorrect")
	})
	@Operation(summary = "login for user")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
		Map<String, Object> credentials = authenticationService.login(request.getUserName(), request.getPassWord());
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken((String) credentials.get("token"));
		loginResponse.setUserResponse(mapToResponse(credentials.get("user"), UserResponse.class));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResponse);
	}
	
	@PutMapping("/user/{id}/newPassword")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "login.successfully"),
			@ApiResponse(responseCode = "404", description = "not.found.user")
	})
	@Operation(summary = "change password for new user")
	public ResponseEntity<String> changePassword(@PathVariable String id, @RequestParam String newPassword) {
		newPassword = passwordEncoder.encode(newPassword);
		this.authenticationService.changePasswordNewUser(id, newPassword);
		return ResponseEntity.ok().body("change.password.success!new.password.is." + newPassword);
	}
	
}
