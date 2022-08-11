//package com.nashtech.rookies.java05.AssetManagement.service.impl;
//
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Role;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
//import com.nashtech.rookies.java05.AssetManagement.model.enums.UserRole;
//import com.nashtech.rookies.java05.AssetManagement.model.enums.UserStatus;
//import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
//import com.nashtech.rookies.java05.AssetManagement.security.jwt.JwtProvider;
//import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.AuthenticationServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//public class TestAuthenticationServiceImpl {
//    private AuthenticationServiceImpl authenticationService;
//    private UserRepository userRepository;
//    private AuthenticationManager authenticationManager;
//
//    private JwtProvider jwtProvider;
//
//    @BeforeEach
//    public void setUp() {
//        userRepository = mock(UserRepository.class);
//        authenticationManager = mock(AuthenticationManager.class);
//        jwtProvider = mock(JwtProvider.class);
//        authenticationService = new AuthenticationServiceImpl(userRepository, authenticationManager, jwtProvider);
//    }
//
//    @Test
//    public void givenValidUsernameAndPassword_shouldLoginSuccess_thenReturnUserAndToken() {
//        User user = new User();
//        Role role = new Role();
//        role.setName(UserRole.ADMIN);
//        user.setRole(role);
//        user.setUserName("viet");
//        Authentication authentication = mock(Authentication.class);
//
//        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("viet", eq("123456"))))
//                .thenReturn(authentication);
//        when(userRepository.findByUserName("viet")).thenReturn(Optional.of(user));
//        assertThat(user.getRole().getName()).isEqualTo(UserRole.ADMIN);
//        String token = jwtProvider.createToken(eq("viet"), eq("123456"));
//        Map<String, Object> expect = new HashMap<>();
//        expect.put("user", user);
//        expect.put("token", token);
//        assertThat(authenticationService.login("viet", eq("123456"))).isEqualTo(expect);
//    }
//
//    @Test
//    public void givenNewUserAndNewPassword_shouldChangePasswordForNewUser() {
//        User user = new User();
//        Role role = new Role();
//        role.setName(UserRole.ADMIN);
//        user.setId("SD1000");
//        user.setRole(role);
//        authenticationService = mock(AuthenticationServiceImpl.class);
//
//        when(userRepository.getById("SD1000")).thenReturn(user);
//        assertThat(user.getRole().getName()).isEqualTo(UserRole.ADMIN);
//        user.setStatus(UserStatus.ACTIVE);
//        user.setPassWord("1234567");
//        when(userRepository.save(user)).thenReturn(user);
//        authenticationService.changePasswordNewUser("SD1000", "1234567");
//        verify(authenticationService).changePasswordNewUser("SD1000", "1234567");
//    }
//
//    @Test
//    public void givenAlreadyUserLogin_shouldLogoutSuccess() {
//        SecurityContextLogoutHandler securityContextLogoutHandler = mock(SecurityContextLogoutHandler.class);
//        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
//        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
//        authenticationService = mock(AuthenticationServiceImpl.class);
//
//        securityContextLogoutHandler.setClearAuthentication(true);
//        securityContextLogoutHandler.logout(httpServletRequest, httpServletResponse, null);
//        verify(securityContextLogoutHandler).logout(httpServletRequest, httpServletResponse, null);
//        authenticationService.logout(httpServletRequest, httpServletResponse);
//        verify(authenticationService).logout(httpServletRequest, httpServletResponse);
//    }
//}
