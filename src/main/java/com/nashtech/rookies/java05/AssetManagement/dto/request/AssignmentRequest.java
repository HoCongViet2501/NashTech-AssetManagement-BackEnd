package com.nashtech.rookies.java05.AssetManagement.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AssignmentRequest {

	private String asset;

	private String user;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date assignedDate;

	private String note;

	private String state;

	private boolean status;
	
//	private boolean hasReturning;

}
