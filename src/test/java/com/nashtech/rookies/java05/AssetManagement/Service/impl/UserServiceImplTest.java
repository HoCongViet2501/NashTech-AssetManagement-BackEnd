//package com.nashtech.rookies.java05.AssetManagement.Service.impl;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.MatcherAssert.assertThat;
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
//import com.nashtech.rookies.java05.AssetManagement.model.enums.UserRole;
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
//    Information information;
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
//        information = mock(Information.class);
//        role = mock(Role.class);
//        userResponse = mock(UserResponse.class);
//        informationResponse = mock(InformationResponse.class);
//        
//    }
//    
//    @Test
//	public void createUser_ShouldReturnUser_WhenDataValid() throws ParseException {
//
//		Role role = new Role();
//		role.setId(1L);
//		role.setName(UserRole.ADMIN);
//
//		SignupRequest signupRequestDTO = new SignupRequest();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		signupRequestDTO.setFirstName("Hai");
//		signupRequestDTO.setLastName("Pham Dang");
//		signupRequestDTO.setDateOfBirth(formatter.parse("2000-12-06"));
//		signupRequestDTO.setGender(true);
//		signupRequestDTO.setJoinedDate(formatter.parse("2022-07-26"));
//		signupRequestDTO.setRole(1L);
//		signupRequestDTO.setLocation("HCM");
////		userServiceImpl = mock(UserServiceImpl.class);
//
//		
//		Information information = MappingData.mapping(signupRequestDTO, Information.class);
//		User user = MappingData.mapping(signupRequestDTO, User.class);
//
//		when(MappingData.mapping(signupRequestDTO, User.class)).thenReturn(user);
//		when(MappingData.mapping(signupRequestDTO, Information.class)).thenReturn(information);
//		System.out.println("User: " + user.toString() );
//		System.out.println("Information: " + information.toString() );
////		userServiceImpl.checkDate(signupRequestDTO);
////		verify(userServiceImpl).checkDate(signupRequestDTO);
//		when(roleRepository.findById(signupRequestDTO.getRole())).thenReturn(Optional.of(role));
//
//		when(userRepository.save(user)).thenReturn(user);
//		information.setUser(user);
//
//		// Information saveInformation = new Information();
//		when(informationRepository.save(information)).thenReturn(information);
//
//		InformationResponse informationResponse = new InformationResponse();
//
//		when(MappingData.mapping(information, InformationResponse.class)).thenReturn(informationResponse);
//
//		when(MappingData.mapping(user, UserResponse.class)).thenReturn(userResponse);
//		userResponse.setInformationResponse(informationResponse);
//		UserResponse result = userServiceImpl.createUser(signupRequestDTO);
////		System.out.println("UserResponse: " + result.getUserId() );
//		assertThat(result, is(userResponse));
//	}
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
//    
////    @Test
////	public void editUser_ShouldReturnUser_WhenDataValid() throws ParseException {
////    	
////    	
////    	String id ="SD0002";
////    	
////    	SignupRequest signupRequestDTO = new SignupRequest();
////		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
////		signupRequestDTO.setFirstName("Karney");
////		signupRequestDTO.setLastName("Freeland");
////		signupRequestDTO.setDateOfBirth(formatter.parse("1995-04-30"));
////		signupRequestDTO.setGender(true);
////		signupRequestDTO.setJoinedDate(formatter.parse("2022-06-06"));
////		signupRequestDTO.setRole(2L);
////		signupRequestDTO.setLocation("HCM");
////    	
////		User user = MappingData.mapping(signupRequest, User.class);
////        
////    	Information information = MappingData.mapping(signupRequest, Information.class);
////    	information.setUser(user);
////        
////        when(MappingData.mapping(signupRequestDTO, User.class)).thenReturn(user);
////        System.out.println("User:  "+user.getId());
////		when(MappingData.mapping(signupRequestDTO, Information.class)).thenReturn(information);
////		
////		when(informationRepository.findByUserId(id)).thenReturn(Optional.of(information));
////		
////		when(roleRepository.findById(signupRequestDTO.getRole())).thenReturn(Optional.of(role));
////		
////		information.setDateOfBirth(signupRequestDTO.getDateOfBirth());
////		information.setJoinedDate(signupRequestDTO.getJoinedDate());
////        information.setGender(signupRequestDTO.isGender());
////		when(userRepository.save(user)).thenReturn(user);
////		information.setUser(user);
////
////		when(informationRepository.save(information)).thenReturn(information);
////
////		InformationResponse informationResponse = new InformationResponse();
////
////		when(MappingData.mapping(information, InformationResponse.class)).thenReturn(informationResponse);
////
////		when(MappingData.mapping(user, UserResponse.class)).thenReturn(userResponse);
////		userResponse.setInformationResponse(informationResponse);
////		UserResponse result = userServiceImpl.editUserInformation(id,signupRequestDTO);
////		assertThat(result, is(userResponse));
////		
////    }
//}
