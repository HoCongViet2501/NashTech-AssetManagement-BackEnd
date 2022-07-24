package com.nashtech.rookies.java05.AssetManagement.service;

import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.response.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;

@Service
public interface UserService {
	public void checkDate(SignupRequest signupRequest);
	public UserResponse createUser(SignupRequest signupRequest);
}
