//package com.nashtech.rookies.java05.AssetManagement.service.impl;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Optional;
//
//import com.nashtech.rookies.java05.AssetManagement.dto.request.ChangePasswordRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//
//import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
//import com.nashtech.rookies.java05.AssetManagement.dto.response.InformationResponse;
//import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
//import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Role;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
//import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
//import com.nashtech.rookies.java05.AssetManagement.repository.RoleRepository;
//import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
//import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.UserServiceImpl;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class UserServiceImplTest {
//    
//    UserRepository userRepository;
//    
//    InformationRepository informationRepository;
//    
//    RoleRepository roleRepository;
//    
//    UserServiceImpl userServiceImpl;
//    
//    SignupRequest signupRequest;
//    
//    User user;
//    
//    Role role;
//    
//    ModelMapper modelMapper;
//    
//    MappingData mappingData;
//    
//    UserResponse userResponse;
//    
//    InformationResponse informationResponse;
//    
//    PasswordEncoder passwordEncoder;
//    
//    @BeforeEach
//    public void beforeEach() {
//        userRepository = mock(UserRepository.class);
//        informationRepository = mock(InformationRepository.class);
//        roleRepository = mock(RoleRepository.class);
//        modelMapper = spy(ModelMapper.class);
//        mappingData = new MappingData(modelMapper);
//        passwordEncoder = new BCryptPasswordEncoder();
//        userServiceImpl = new UserServiceImpl(userRepository, informationRepository, roleRepository);
//        signupRequest = mock(SignupRequest.class);
//        user = mock(User.class);
//        role = mock(Role.class);
//        userResponse = mock(UserResponse.class);
//        informationResponse = mock(InformationResponse.class);
//        
//    }
//    
//    @Test
//    public void createUser_ShouldReturnStatusOK_WhenDataValid() throws ParseException {
//        
//        SignupRequest signupRequestDTO = new SignupRequest();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        signupRequestDTO.setFirstName("Hai");
//        signupRequestDTO.setLastName("Pham Dang");
//        signupRequestDTO.setDateOfBirth(formatter.parse("2000-12-06"));
//        signupRequestDTO.setGender(true);
//        signupRequestDTO.setJoinedDate(formatter.parse("2022-25-07"));
//        signupRequestDTO.setRole((long) 1);
//        signupRequestDTO.setLocation("HCM");
//        
//        Information information = MappingData.mapping(signupRequestDTO, Information.class);
//        when(MappingData.mapping(signupRequestDTO, Information.class)).thenReturn(information);
//        when(MappingData.mapping(signupRequestDTO, User.class)).thenReturn(user);
//        
//        // User saveUser = new User();
//        verify(user).setUserName("fjdsklf");
//        when(userRepository.save(user)).thenReturn(user);
//        information.setUser(user);
//        
//        // Information saveInformation = new Information();
//        when(informationRepository.save(information)).thenReturn(information);
//        
//        InformationResponse informationResponse = new InformationResponse();
//        
//        when(MappingData.mapping(information, InformationResponse.class)).thenReturn(informationResponse);
//        
//        when(MappingData.mapping(user, UserResponse.class)).thenReturn(userResponse);
//        userResponse.setInformationResponse(informationResponse);
//        UserResponse result = userServiceImpl.createUser(signupRequestDTO);
////        assertThat(result, is(userResponse));
//    }
//    
//    @Test
//    public void changePassword_shouldChangePasswordSuccess_andSavePasswordEncoded() {
//        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
//        changePasswordRequest.setNewPassword("123456");
//        changePasswordRequest.setOldPassword("1234567");
//        changePasswordRequest.setUserId("SD001");
//        User user = spy(User.class);
//        user.setPassWord(passwordEncoder.encode("1234567"));
//        userServiceImpl = mock(UserServiceImpl.class);
//        
//        assertThat(changePasswordRequest.getUserId()).isEqualTo("SD001");
//        when(userRepository.findUserById("SD001")).thenReturn(Optional.of(user));
//        assertThat(passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassWord()))
//                .isEqualTo(true);
//        user.setPassWord(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
//        when(userRepository.save(user)).thenReturn(user);
//        userServiceImpl.changePassword(changePasswordRequest);
//        verify(userServiceImpl).changePassword(changePasswordRequest);
//    }
//}
