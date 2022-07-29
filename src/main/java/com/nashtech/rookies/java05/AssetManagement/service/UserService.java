package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;

@Service
public interface UserService {

	public UserResponse createUser(SignupRequest signupRequest);

	public List<UserDetailResponse> getAllUserSameLocation(String location);

	public List<UserDetailResponse> searchUser(String content, String location);

	public boolean checkUserIsAvailable(String staffCode);

	public ResponseEntity<Object> disableUser(String staffCode);

	public UserResponse editUserInformation(String id, SignupRequest signupRequest);

	// public Optional<Information> getUserInformationById(String id);

	public List<UserDetailResponse> getUserInformationById(String id);
}
