package com.nashtech.rookies.java05.AssetManagement.exception;

public class ResourceCheckDateException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ResourceCheckDateException(String message){
        super(message);
    }
}
