package com.nashtech.rookies.java05.AssetManagement.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordRequest {
    @NotNull(message = "userId.is.required")
    private String userId;
    @NotNull(message = "newPassword.is.required")
    private String newPassword;
    @NotNull(message = "oldPassword.is.required")
    private String oldPassword;
}
