package com.nashtech.rookies.java05.AssetManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String statusCode;
    private String message;
    private String details;

    /**
     * @param statusCode
     * @param message
     * @param details
     */
    public ErrorResponse(String statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }
}
