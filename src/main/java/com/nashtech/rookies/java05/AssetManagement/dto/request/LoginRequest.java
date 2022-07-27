package com.nashtech.rookies.java05.AssetManagement.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	
	@NotNull(message = "required")
	private String username;
	
	@NotNull(message = "required")
	private String password;
}

