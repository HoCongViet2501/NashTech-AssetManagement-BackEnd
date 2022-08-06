package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.model.enums.UserStatus;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.security.jwt.JwtProvider;
import com.nashtech.rookies.java05.AssetManagement.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    
    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }
    
    
    @Override
    public Map<String, Object> login(String userName, String passWord) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, passWord));
            User user = userRepository.findByUserName(userName).orElseThrow(() -> new ResourceNotFoundException("User Not Found..."));
            String userRole = String.valueOf(user.getRole().getName());
            String token = jwtProvider.createToken(userName, userRole);
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);
            return response;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("incorrect.password.or.username ", e);
        }
    }
    
    @Override
    public void changePasswordNewUser(String userId, String newPassWord) {
        User user = this.userRepository.getById(userId);
        if (Objects.equals(user.getStatus(), UserStatus.NEW)) {
            user.setPassWord(newPassWord);
            user.setStatus(UserStatus.ACTIVE);
            this.userRepository.save(user);
        }
    }
    
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(httpServletRequest, httpServletResponse, null);
    }
}
