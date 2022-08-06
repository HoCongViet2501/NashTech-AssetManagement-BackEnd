package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentResponse;

@Service
public interface AssignmentService {
	AssignmentResponse createAssignment(AssignmentRequest assignmentRequest, String userName);

	AssignmentResponse editAssignment(AssignmentRequest assignmentRequest, Long id);

	AssignmentDetailResponse getAssignment(Long id);

	ResponseEntity<?> deleteAssignment(Long id);

	public List<AssignmentDetailResponse> getAllAssignmentByLocation(String location);

	public List<AssignmentDetailResponse> searchAssignment(String content, String location);

}
