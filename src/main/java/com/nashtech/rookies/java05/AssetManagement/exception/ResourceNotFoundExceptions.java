package com.nashtech.rookies.java05.AssetManagement.exception;

public class ResourceNotFoundExceptions extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public void ResourceNotFoundExceptions() {
	}

	public ResourceNotFoundExceptions(String message) {
        super(message);
    }
}
