package com.nashtech.rookies.java05.AssetManagement.dto.response;

import java.util.Date;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.model.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
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

	public static AssignmentDetailResponse buildFromAssignment(Assignment assignment) {
		return new AssignmentDetailResponse(assignment.getId(),
				assignment.getAsset().getId(),
				assignment.getAsset().getName(),
				assignment.getUser().getUserName(),
				assignment.getCreator().getUserName(),
				assignment.getAssignedDate(),
				assignment.getState(),
				assignment.getNote(),
				assignment.isHasReturning()
				);
	}

	public AssignmentDetailResponse(Long id, String assetCode, String assetName, String assignedTo, String assignedBy,
			Date assignedDate, String state, String note, boolean hasReturning) {
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
	}


}
