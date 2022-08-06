package com.nashtech.rookies.java05.AssetManagement.dto.response;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AssignmentDetailResponse {
    private Long id;
    
    private String assetCode;
    
    private String assetName;
    
    private String specification;
    
    private String assignTo;
    
    private String assignBy;
    
    private Date assignedDate;
    
    private String note;
    
    private String state;
    
    private boolean status;
    
    
    public static AssignmentDetailResponse build(Assignment assignment) {
        return new AssignmentDetailResponse(assignment.getId(),
                assignment.getAsset().getId(),
                assignment.getAsset().getName(),
                assignment.getAsset().getSpecification(),
                assignment.getUser().getUserName(),
                assignment.getCreator().getUserName(),
                assignment.getAssignedDate(),
                assignment.getNote(),
                assignment.getState(),
                assignment.isStatus());
    }
}
