package com.nashtech.rookies.java05.AssetManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String statusCode;
    private String message;

    /**
     * @param statusCode
     * @param message
     * @param details
     */
    public ErrorResponse(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
