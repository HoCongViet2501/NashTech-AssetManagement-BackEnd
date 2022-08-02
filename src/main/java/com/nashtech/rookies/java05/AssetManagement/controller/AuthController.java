package com.nashtech.rookies.java05.AssetManagement.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.nashtech.rookies.java05.AssetManagement.dto.request.ChangePasswordRequest;
import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
import com.nashtech.rookies.java05.AssetManagement.service.AuthenticationService;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.java05.AssetManagement.dto.request.LoginRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.LoginResponse;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    @Autowired
    public AuthController(AuthenticationService authenticationService, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }
    
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login.successfully"),
            @ApiResponse(responseCode = "403", description = "username.or.password.is.incorrect")
    })
    @Operation(summary = "login for user")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        Map<String, Object> credentials = authenticationService.login(request.getUsername(), request.getPassword());
        String token = (String) credentials.get("token");
        User user = MappingData.mapping(credentials.get("user"), User.class);
        
        LoginResponse loginResponse = LoginResponse.builder().token(token).location(user.getInformation().getLocation()).username(user.getUserName())
                .role(user.getRole()).status(user.getStatus()).userId(user.getId()).build();
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResponse);
    }
    
    @PutMapping("/user/{id}/{newPassword}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login.successfully"),
            @ApiResponse(responseCode = "404", description = "not.found.user")
    })
    @Operation(summary = "change password for new user")
    public ResponseEntity<String> changePassword(@PathVariable String id, @PathVariable String newPassword) {
        newPassword = passwordEncoder.encode(newPassword);
        this.authenticationService.changePasswordNewUser(id, newPassword);
        return ResponseEntity.ok().body("change.password.successfully!");
    }
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "logout.successfully"),
            @ApiResponse(responseCode = "500", description = "request.logout.have.error")
    })
    @Operation(summary = "logout for user")
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.authenticationService.logout(httpServletRequest, httpServletResponse);
        return ResponseEntity.ok("Logout.user.successfully!");
    }
    
    @Operation(summary = "change password for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "change password success fully"),
            @ApiResponse(responseCode = "404", description = "not found user"),
            @ApiResponse(responseCode = "403", description = "Access denied!"),
            @ApiResponse(responseCode = "400", description = "old password incorrect")
    })
    @PostMapping("/user/changePassword")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        this.userService.changePassword(changePasswordRequest);
        return ResponseEntity.ok("change.password.successfully!");
    }
}
