package com.nashtech.rookies.java05.AssetManagement.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.nashtech.rookies.java05.AssetManagement.dto.UserResponseDto;
import com.nashtech.rookies.java05.AssetManagement.model.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserResponseDto> getAllUserSameLocation(String location) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UserResponseDto> searchUserByIdAndUsername(String id, String username) {
        // TODO Auto-generated method stub
        return null;
    }

}
