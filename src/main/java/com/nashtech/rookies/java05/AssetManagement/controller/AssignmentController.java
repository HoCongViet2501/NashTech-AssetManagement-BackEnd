package com.nashtech.rookies.java05.AssetManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentResponse;
import com.nashtech.rookies.java05.AssetManagement.service.AssignmentService;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

	@Autowired
	AssignmentService assignmentService;

	@PostMapping("/createAssign/{assignedby}")
	AssignmentResponse createAssignment(@PathVariable String assignedby,
			@Valid @RequestBody AssignmentRequest assignmentRequest) {
		return assignmentService.createAssignment(assignmentRequest, assignedby);
	}

	@GetMapping("/getAll/{location}")
	public List<AssignmentDetailResponse> getAllAssignment(@PathVariable String location) {
		return assignmentService.getAllAssignmentByLocation(location);
	}

	@PutMapping("/edit/{id}")
	AssignmentResponse editAssignment(@PathVariable Long id, @Valid @RequestBody AssignmentRequest assignmentRequest) {
		return assignmentService.editAssignment(assignmentRequest, id);
	}

	@GetMapping("/search/{location}/{content}")
	public List<AssignmentDetailResponse> searchAssignment(@PathVariable String location,
			@PathVariable String content) {
		return assignmentService.searchAssignment(content, location);
	}
	
	@DeleteMapping("/disable/{id}")
	public ResponseEntity<?> disableUser(@PathVariable Long id) {
		return this.assignmentService.deleteAssignment(id);
	}
}
