package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentStaffResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;

public interface AssignmentService {
	List<AssignmentStaffResponse> getListAssignments(String userId);

	AssignmentStaffResponse getAssignmentById(long id);

	AssignmentStaffResponse updateStateAssignment(long id, String status);

	AssignmentDetailResponse createAssignment(AssignmentRequest assignmentRequest);

	AssignmentDetailResponse editAssignment(AssignmentRequest assignmentRequest, Long id);

	AssignmentDetailResponse getAssignment(Long id);

	ResponseEntity<?> deleteAssignment(Long id);

	List<AssignmentDetailResponse> getAllAssignmentByLocation();

	List<AssignmentDetailResponse> searchAssignment(String content);

	List<AssetResponse> getAllAssetByLocationAndState();

	List<AssetResponse> searchAssetByLocationAndState(String content);

	List<UserDetailResponse> getAllUserByLocationAndStatus();

	List<UserDetailResponse> searchUserByLocationAndStatus(String content);
}
