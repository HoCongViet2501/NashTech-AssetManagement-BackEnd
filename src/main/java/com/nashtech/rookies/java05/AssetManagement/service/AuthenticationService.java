package com.nashtech.rookies.java05.AssetManagement.service;

import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;

import java.util.Map;

public interface AuthenticationService {
	Map<String, Object> login(String userName, String passWord);
	
	void changePasswordNewUser(String userId, String newPassword);
}
