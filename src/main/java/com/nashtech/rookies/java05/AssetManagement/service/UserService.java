package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;
import java.util.Optional;

import com.nashtech.rookies.java05.AssetManagement.dto.UserResponseDto;

public interface UserService {
    public List<UserResponseDto> getAllUserSameLocation(String location);

    public List<UserResponseDto> searchUserByIdAndUsername(String id, String username);
}
