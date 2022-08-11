package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.nashtech.rookies.java05.AssetManagement.dto.request.ChangePasswordRequest;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;

@Service
public interface UserService {
    
    UserResponse createUser(SignupRequest signupRequest);
    
    List<UserDetailResponse> getAllUserSameLocation();
    
    List<UserDetailResponse> searchUser(String content);
    
    boolean checkUserIsAvailable(String staffCode);
    
    ResponseEntity<Object> disableUser(String staffCode);
    
    UserResponse editUserInformation(String id, SignupRequest signupRequest);
    
    List<UserDetailResponse> getUserInformationById(String id);
    
    void changePassword(ChangePasswordRequest changePasswordRequest);
}
