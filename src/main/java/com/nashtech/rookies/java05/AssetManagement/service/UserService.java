package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;

import com.nashtech.rookies.java05.AssetManagement.dto.UserResponseDto;

public interface UserService {
    public List<UserResponseDto> getAllUserSameLocation(String location);
}
