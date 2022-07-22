package com.nashtech.rookies.java05.AssetManagement.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.InformationResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.UserResponse;

@Service
public interface UserService {
	public void checkDate(SignupRequest signupRequest);
	public UserResponse createUser(SignupRequest signupRequest);
}
