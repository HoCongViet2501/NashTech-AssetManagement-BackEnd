package com.nashtech.rookies.java05.AssetManagement.controller;

import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {
    
    UserService userService;
    
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
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
    
    @Operation(summary = "change password for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "change password success fully"),
            @ApiResponse(responseCode = "404", description = "not found user"),
            @ApiResponse(responseCode = "403", description = "Access denied, please enter token!")
    })
    @PutMapping("/{id}/{password}")
    public ResponseEntity<String> changePassword(@PathVariable String id, @PathVariable String password) {
        password = this.passwordEncoder.encode(password);
        this.userService.changePassword(id, password);
        return ResponseEntity.ok("change.password.successfully!");
    }
}
