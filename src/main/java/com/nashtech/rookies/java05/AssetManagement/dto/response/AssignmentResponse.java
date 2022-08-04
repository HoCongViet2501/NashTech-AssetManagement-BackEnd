package com.nashtech.rookies.java05.AssetManagement.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class AssignmentResponse {
	private Long id;

	private AssetResponse assetResponse;

	private UserResponse user;

	private UserResponse createUser;

	private Date assignedDate;

	private String state;

	private String note;

	private String status;
}
