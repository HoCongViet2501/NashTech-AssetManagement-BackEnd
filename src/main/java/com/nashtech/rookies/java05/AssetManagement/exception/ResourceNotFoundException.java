package com.nashtech.rookies.java05.AssetManagement.exception;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(final String message){
        super(message);
    }
}
