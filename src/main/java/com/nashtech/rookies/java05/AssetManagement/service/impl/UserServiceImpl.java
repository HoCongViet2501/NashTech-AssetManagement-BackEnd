package com.nashtech.rookies.java05.AssetManagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Information;
import com.nashtech.rookies.java05.AssetManagement.Model.repository.InformationRepository;
import com.nashtech.rookies.java05.AssetManagement.dto.UserResponseDto;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private InformationRepository informationRepository;

    @Override
    public List<UserResponseDto> getAllUserSameLocation(String location) {
        List<Information> lists = this.informationRepository.findByLocation(location);
        if (lists.isEmpty()) {
            throw new ResourceNotFoundException("No User Founded");
        }

        return lists.stream()
                .map(UserResponseDto::buildFromInfo)
                .collect(Collectors.toList());
    }

}
