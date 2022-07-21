package com.nashtech.rookies.java05.AssetManagement.exception;

public class InvalidException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public void InvalidField() {
    }

	public InvalidException(String message) {
        super(message);
    }
}
