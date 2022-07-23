package com.nashtech.rookies.java05.AssetManagement.dto.response;

import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserResponse {
	private String id;
	
	private String userName;
	
	private String passWord;
	
	private Role role;
	
	private String status;
}
