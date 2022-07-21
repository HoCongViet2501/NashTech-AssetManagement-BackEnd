package com.nashtech.rookies.java05.AssetManagement.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.Model.DTO.InformationDTO;
import com.nashtech.rookies.java05.AssetManagement.Model.DTO.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.response.UserResponse;

@Service
public interface UserService {
	public void checkDate(SignupRequest signupRequest);
	public UserResponse createUser(SignupRequest signupRequest);
}
