package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
  @Mock
  InformationRepository informationRepository;

  @Mock
  Information information;

  @InjectMocks
  UserServiceImpl userServiceImpl;

  @Test
  public void getAllUserSameLocation_Given_ValidLocation_ShouldReturnDto_WhenDataValid() {

  }
}
