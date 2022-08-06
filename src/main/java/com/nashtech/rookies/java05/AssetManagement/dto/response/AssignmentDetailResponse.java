package com.nashtech.rookies.java05.AssetManagement.dto.response;

import java.util.Date;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentDetailResponse {
	private Long id;

	private String assetCode;

	private String assetName;

	private String assignedTo;

	private String assignedBy;

	private Date assignedDate;

	private String note;

	private String state;

	private boolean hasReturning;

	private boolean status;

	public static AssignmentDetailResponse buildFromAssignment(Assignment assignment) {
		return new AssignmentDetailResponse(assignment.getId(),
				assignment.getAsset().getId(),
				assignment.getAsset().getName(),
				assignment.getUser().getUserName(),
				assignment.getCreator().getUserName(),
				assignment.getAssignedDate(),
				assignment.getState(),
				assignment.getNote(),
				assignment.isHasReturning(),
				assignment.isStatus());
	}

	public AssignmentDetailResponse(Long id, String assetCode, String assetName, String assignedTo, String assignedBy,
			Date assignedDate, String state, String note, boolean hasReturning, boolean status) {
		super();
		this.id = id;
		this.assetCode = assetCode;
		this.assetName = assetName;
		this.assignedTo = assignedTo;
		this.assignedBy = assignedBy;
		this.assignedDate = assignedDate;
		this.state = state;
		this.note = note;
		this.hasReturning = hasReturning;
		this.status = status;
	}

}
