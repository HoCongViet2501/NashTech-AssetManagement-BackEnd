package com.nashtech.rookies.java05.AssetManagement.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(final String message){
        super(message);
    }
}
