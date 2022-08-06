package com.nashtech.rookies.java05.AssetManagement.controller.staff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentStaffResponse;
import com.nashtech.rookies.java05.AssetManagement.service.AssignmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/staff/assignments")
public class AssignmentStaffController {
    private final AssignmentService assignmentService;
    
    @Autowired
    public AssignmentStaffController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }
    
    @Operation(summary = "get list assignment have assignDate < current date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get list assignment"),
            @ApiResponse(responseCode = "500", description = "error!"),
            @ApiResponse(responseCode = "404",description = "not found user!")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getListAssignmentsSortedDate(@PathVariable String userId) {
        List<AssignmentStaffResponse> assignmentResponses = this.assignmentService.getListAssignments(userId);
        return ResponseEntity.ok().body(assignmentResponses);
    }
    
    @Operation(summary = "get assignment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get list assignment"),
            @ApiResponse(responseCode = "404", description = "not found assignment!")
    })
    @GetMapping("/getOne/{id}")
    public ResponseEntity<Object> getAssignmentById(@PathVariable long id) {
        return ResponseEntity.ok().body(this.assignmentService.getAssignmentById(id));
    }
    
    @Operation(summary = "update state assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get list assignment"),
            @ApiResponse(responseCode = "404", description = "not found assignment!"),
            @ApiResponse(responseCode = "400", description = "bad request , check input field!")
    })
    @PatchMapping("/{id}/state")
    public ResponseEntity<Object> updateSateAssignment(@PathVariable long id, @RequestParam String state) {
        AssignmentStaffResponse assignmentDetailResponse = this.assignmentService.updateStateAssignment(id, state);
        return ResponseEntity.ok().body(assignmentDetailResponse);
    }
}
