package com.nashtech.rookies.java05.AssetManagement.exception;

public class ResourceCheckException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ResourceCheckException(String message){
        super(message);
    }
}
