// package com.nashtech.rookies.java05.AssetManagement.service.impl;

// import com.nashtech.rookies.java05.AssetManagement.dto.request.LoginRequest;
// import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
// import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
// import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.AuthenticationServiceImpl;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.security.authentication.AuthenticationManager;

// import static org.mockito.Mockito.mock;

// public class TestAuthenticationServiceImpl {
// 	private AuthenticationServiceImpl authenticationService;
// 	private UserRepository userRepository;
// 	private MappingData mappingData;
	
// 	private AuthenticationManager authenticationManager;
	
// 	@BeforeEach
// 	public void setUp() {
// 		authenticationService = mock(AuthenticationServiceImpl.class);
// 		userRepository = mock(UserRepository.class);
// 		mappingData = mock(MappingData.class);
// 		authenticationManager = mock(AuthenticationManager.class);
// 	}
	
// 	@Test
// 	public void givenValidUsernameAndPassword_shouldLoginSuccess_thenReturnUserAndToken() {
// 		LoginRequest loginRequest = mock(LoginRequest.class);
// 		loginRequest.setPassword("123456");
// 		loginRequest.setUsername("viet");
		
		
// 	}
// }
