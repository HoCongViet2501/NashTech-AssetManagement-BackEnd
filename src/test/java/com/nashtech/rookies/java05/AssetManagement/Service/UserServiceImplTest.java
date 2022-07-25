package com.nashtech.rookies.java05.AssetManagement.Service;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nashtech.rookies.java05.AssetManagement.config.ModelMapperConfig;
import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.RoleRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.UserServiceImpl;

public class UserServiceImplTest {

	private UserRepository userRepository;
	private InformationRepository informationRepository;
	private RoleRepository roleRepository;
	
	private UserServiceImpl userServiceImpl;
	
	SignupRequest signupRequest ;

	private Information information;
	private User user;
	private PasswordEncoder encoder;

	UserResponse userResponse;
	private ModelMapperConfig modelMapperConfig;
	
	@BeforeEach
	public void beforeEach() {
		userRepository = mock(UserRepository.class);
		informationRepository = mock(InformationRepository.class);
		roleRepository = mock(RoleRepository.class);
		modelMapperConfig = mock(ModelMapperConfig.class);
//		
//		userServiceImpl = new UserServiceImpl(userRepository,informationRepository,roleRepository) {
//			
//		}
		
	}
	
	
	
}
