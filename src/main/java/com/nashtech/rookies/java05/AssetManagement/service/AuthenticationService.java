package com.nashtech.rookies.java05.AssetManagement.service;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface AuthenticationService {
    Map<String, Object> login(String userName, String passWord);
    
    void changePasswordNewUser(String userId, String newPassword);
    
    void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
