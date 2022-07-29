package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;

@Service
public interface UserService {

	public UserResponse createUser(SignupRequest signupRequest);

	public List<UserDetailResponse> getAllUserSameLocation(String location);

	public List<UserDetailResponse> searchUser(String content, String location);

	public boolean checkUserIsAvailable(String staffCode);

	public ResponseEntity<Object> disableUser(String staffCode);

	public UserResponse editUserInformation(String id, SignupRequest signupRequest);

	public List<UserDetailResponse> getUserInformationById(String id);

	public void changePassword(String userId, String newPassword);
}
