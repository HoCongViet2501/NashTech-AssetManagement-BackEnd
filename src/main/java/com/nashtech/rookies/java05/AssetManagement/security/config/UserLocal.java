package com.nashtech.rookies.java05.AssetManagement.security.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;

@Component
public class UserLocal {


    public String getLocalUserName(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(userName);

        if(userName != null)
        {
            return userName;
        }
        throw new ResourceNotFoundException("Cannot recognize user. Maybe you haven't log in");
    }
}
