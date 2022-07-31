package com.nashtech.rookies.java05.AssetManagement.service;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    Map<String, Object> login(String userName, String passWord);
    
    void changePasswordNewUser(String userId, String newPassword);
    
    void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
