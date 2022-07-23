package com.nashtech.rookies.java05.AssetManagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	
	@NotNull(message = "required")
	private String userName;
	
	@NotNull(message = "required")
	private String passWord;
}

