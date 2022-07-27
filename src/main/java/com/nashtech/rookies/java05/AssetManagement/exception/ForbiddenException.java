package com.nashtech.rookies.java05.AssetManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message){
        super(message);
    }
    public ForbiddenException(String message, Throwable throwable){
        super(message,throwable);
    }
}
