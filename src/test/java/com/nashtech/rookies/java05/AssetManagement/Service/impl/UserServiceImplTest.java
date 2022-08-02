package com.nashtech.rookies.java05.AssetManagement.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.InformationResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Role;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.RoleRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.UserServiceImpl;

public class UserServiceImplTest {
    
    UserRepository userRepository;
    
    InformationRepository informationRepository;
    
    RoleRepository roleRepository;
    
    UserServiceImpl userServiceImpl;
    
    SignupRequest signupRequest;
    
    User user;
    
    Role role;
    
    ModelMapper modelMapper;
    
    MappingData mappingData;
    
    UserResponse userResponse;
    
    InformationResponse informationResponse;
    
    @BeforeEach
    public void beforeEach() {
        userRepository = mock(UserRepository.class);
        informationRepository = mock(InformationRepository.class);
        roleRepository = mock(RoleRepository.class);
        modelMapper = spy(ModelMapper.class);
        mappingData = new MappingData(modelMapper);
		userServiceImpl = new UserServiceImpl(userRepository, informationRepository, roleRepository);
        signupRequest = mock(SignupRequest.class);
        user = mock(User.class);
        
        role = mock(Role.class);
        
        userResponse = mock(UserResponse.class);
        informationResponse = mock(InformationResponse.class);
        
    }
    
    @Test
    public void createUser_ShouldReturnStatusOK_WhenDataValid() throws ParseException {
        
        SignupRequest signupRequestDTO = new SignupRequest();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        signupRequestDTO.setFirstName("Hai");
        signupRequestDTO.setLastName("Pham Dang");
        signupRequestDTO.setDateOfBirth(formatter.parse("2000-12-06"));
        signupRequestDTO.setGender(true);
        signupRequestDTO.setJoinedDate(formatter.parse("2022-25-07"));
        signupRequestDTO.setRole((long) 1);
        signupRequestDTO.setLocation("HCM");
    
        Information information =MappingData.mapping(signupRequestDTO, Information.class);
        when(MappingData.mapping(signupRequestDTO, Information.class)).thenReturn(information);
        when(MappingData.mapping(signupRequestDTO, User.class)).thenReturn(user);
        
        // User saveUser = new User();
        verify(user).setUserName("fjdsklf");
        when(userRepository.save(user)).thenReturn(user);
        information.setUser(user);
        
        // Information saveInformation = new Information();
        when(informationRepository.save(information)).thenReturn(information);
        
        InformationResponse informationResponse = new InformationResponse();
        
        when(MappingData.mapping(information, InformationResponse.class)).thenReturn(informationResponse);
        
        when(MappingData.mapping(user, UserResponse.class)).thenReturn(userResponse);
        userResponse.setInformationResponse(informationResponse);
        UserResponse result = userServiceImpl.createUser(signupRequestDTO);
        assertThat(result, is(userResponse));
    }
    
}
