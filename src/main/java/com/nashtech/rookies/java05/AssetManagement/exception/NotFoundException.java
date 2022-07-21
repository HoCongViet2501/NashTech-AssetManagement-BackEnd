package com.nashtech.rookies.java05.AssetManagement.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(final String message){
        super(message);
    }
}
