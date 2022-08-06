package com.nashtech.rookies.java05.AssetManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(final String message){
        super(message);
    }
}
