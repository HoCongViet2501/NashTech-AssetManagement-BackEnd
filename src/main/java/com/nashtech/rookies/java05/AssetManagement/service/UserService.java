package com.nashtech.rookies.java05.AssetManagement.service;

import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AllUserResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;

@Service
public interface UserService {
    
	public UserResponse createUser(SignupRequest signupRequest);

    public AllUserResponse getAllUserResponse(String location, int raw);

    public AllUserResponse searchUser(String content, String location);
}
