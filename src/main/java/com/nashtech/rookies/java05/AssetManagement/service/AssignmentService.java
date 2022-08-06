package com.nashtech.rookies.java05.AssetManagement.service;

import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;

import java.util.List;

public interface AssignmentService {
    List<AssignmentDetailResponse> getListAssignments(String userId);
    
    AssignmentDetailResponse getAssignmentById(long id);
    
    AssignmentDetailResponse updateStateAssignment(long id , String status);
}
