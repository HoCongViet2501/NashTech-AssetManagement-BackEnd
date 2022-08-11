package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentStaffResponse;

public interface AssignmentService {
    List<AssignmentStaffResponse> getListAssignments(String userId);
    
    AssignmentStaffResponse getAssignmentById(long id);
    
    AssignmentStaffResponse updateStateAssignment(long id, String status);
    
    AssignmentResponse createAssignment(AssignmentRequest assignmentRequest);
    
    AssignmentResponse editAssignment(AssignmentRequest assignmentRequest, Long id);
    
    AssignmentDetailResponse getAssignment(Long id);
    
    ResponseEntity<?> deleteAssignment(Long id);
    
    List<AssignmentDetailResponse> getAllAssignmentByLocation(String location);
    
    List<AssignmentDetailResponse> searchAssignment(String content, String location);
    
    List<AssetResponse> getAllAssetByLocationAndState(String location);
    
    List<AssetResponse> searchAssetByLocationAndState(String location, String content);
}
