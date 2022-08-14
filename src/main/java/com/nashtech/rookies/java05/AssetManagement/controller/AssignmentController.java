package com.nashtech.rookies.java05.AssetManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.service.AssignmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {
    
    @Autowired
    AssignmentService assignmentService;
    
    @Operation(summary = "create new assignment")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    AssignmentDetailResponse createAssignment( @Valid @RequestBody AssignmentRequest assignmentRequest) {
        return assignmentService.createAssignment(assignmentRequest);
    }
    
    @GetMapping("/getAll")
    public List<AssignmentDetailResponse> getAllAssignment() {
        return assignmentService.getAllAssignmentByLocation();
    }
    
    @PutMapping("/edit/{id}")
    AssignmentDetailResponse editAssignment(@PathVariable Long id, @Valid @RequestBody AssignmentRequest assignmentRequest) {
        return assignmentService.editAssignment(assignmentRequest, id);
    }
    
    @GetMapping("/search/{content}")
    public List<AssignmentDetailResponse> searchAssignment(@PathVariable String content) {
        return assignmentService.searchAssignment(content);
    }
    
    @DeleteMapping("/disable/{id}")
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        return this.assignmentService.deleteAssignment(id);
    }
    
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get assignment by id Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")
    })
    @Operation(summary = "get assignment by id", description = "get assignment by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getAssignment/{id}")
    public AssignmentDetailResponse getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignment(id);
    }
    
    @GetMapping("/getAsset")
    public List<AssetResponse> getAllAsset() {
        return assignmentService.getAllAssetByLocationAndState();
    }
    
    @GetMapping("/searchAsset/{content}")
    public List<AssetResponse> searchAsset(@PathVariable String content) {
        return assignmentService.searchAssetByLocationAndState(content);
    }
    
    @GetMapping("/getUser")
    public List<UserDetailResponse> getAllUser() {
        return assignmentService.getAllUserByLocationAndStatus();
    }
    
    @GetMapping("/searchUser/{content}")
    public List<UserDetailResponse> searchUser(@PathVariable String content) {
        return assignmentService.searchUserByLocationAndStatus(content);
    }
}
