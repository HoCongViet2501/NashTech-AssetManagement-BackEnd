package com.nashtech.rookies.java05.AssetManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.java05.AssetManagement.dto.UserResponseDto;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping()
  public List<UserResponseDto> getAllUserSameLocation(String location) {
    return userService.getAllUserSameLocation(location);
  }

  
}
