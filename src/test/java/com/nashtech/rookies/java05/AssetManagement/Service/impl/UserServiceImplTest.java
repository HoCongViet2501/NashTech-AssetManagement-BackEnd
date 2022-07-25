package com.nashtech.rookies.java05.AssetManagement.Service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nashtech.rookies.java05.AssetManagement.config.ModelMapperConfig;
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
	Information information;
	User user;
	Role role;
	MappingData mappingData;
	UserResponse userResponse;

	@BeforeEach
	public void beforeEach() {
		userRepository = mock(UserRepository.class);
		informationRepository = mock(InformationRepository.class);
		roleRepository = mock(RoleRepository.class);
		mappingData = mock(MappingData.class);
		userServiceImpl = new UserServiceImpl(userRepository, informationRepository, roleRepository);
		signupRequest = mock(SignupRequest.class);
		information = mock(Information.class);
		user = mock(User.class);

		role = mock(Role.class);

		userResponse = mock(UserResponse.class);

	}

	@Test
	public void createUser_ShouldReturnStatusOK_WhenDataValid() throws ParseException {

		SignupRequest signupRequest = new SignupRequest();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		signupRequest.setFirstName("Hai");
		signupRequest.setLastName("Pham Dang");
		signupRequest.setDateOfBirth(formatter.parse("2000-12-06"));
		signupRequest.setGender(true);
		signupRequest.setJoinedDate(formatter.parse("2022-25-07"));
		signupRequest.setRole((long) 1);
		signupRequest.setLocation("HCM");

		when(MappingData.mapToEntity(signupRequest, Information.class)).thenReturn(information);
		when(MappingData.mapToEntity(signupRequest, User.class)).thenReturn(user);

		User saveUser = new User();

		when(userRepository.save(user)).thenReturn(saveUser);
		// information.setUser(saveUser);

		Information saveInformation = new Information();
		when(informationRepository.save(information)).thenReturn(saveInformation);

		InformationResponse informationResponse = new InformationResponse();

		when(MappingData.mapToEntity(saveInformation, InformationResponse.class)).thenReturn(informationResponse);

		when(MappingData.mapToEntity(saveUser, UserResponse.class)).thenReturn(userResponse);
		// userResponse.setInformationResponse(informationResponse);
		UserResponse result = userServiceImpl.createUser(signupRequest);
		assertThat(result, is(userResponse));
	}

}
