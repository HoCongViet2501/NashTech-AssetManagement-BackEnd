package com.nashtech.rookies.java05.AssetManagement.dto.response;

import java.util.Date;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignmentDetailResponse {
	private Long id;

	private String assetCode;

	private String assetName;

	private String assignedToId;

	private String assignedTo;

	private String assignedBy;

	private Date assignedDate;

	private String state;

	private String note;

	private boolean status;

	private String specification;

	private boolean hasReturning;

	private String fullName;

	public static AssignmentDetailResponse buildFromAssignment(Assignment assignment) {
		String fullName = assignment.getUser().getInformation().getFirstName() + " "
				+ assignment.getUser().getInformation().getLastName();
		return new AssignmentDetailResponse(
				assignment.getId(),
				assignment.getAsset().getId(),
				assignment.getAsset().getName(),
				assignment.getUser().getId(),
				assignment.getUser().getUserName(),
				assignment.getCreator().getUserName(),
				assignment.getAssignedDate(),
				assignment.getState(),
				assignment.getNote(),
				assignment.isStatus(),
				assignment.getAsset().getSpecification(),
				assignment.isHasReturning(),
				fullName);
	}

}