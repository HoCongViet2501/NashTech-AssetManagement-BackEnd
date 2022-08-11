package com.nashtech.rookies.java05.AssetManagement.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nashtech.rookies.java05.AssetManagement.dto.response.ReturningResponse;
import com.nashtech.rookies.java05.AssetManagement.service.ReturnService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/return")
public class ReturnController {
    @Autowired
    private ReturnService returnService;
    
    @PostMapping("/{assignmentId}")
    @Operation(summary = "Create new request for returning asset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create new request for returning asset successfully"),
            @ApiResponse(responseCode = "400", description = "Some field not correct"),
    })
    public ResponseEntity<Object> createRequestReturningAsset(@PathVariable int assignmentId) {
        return this.returnService.createNewReturningAsset(assignmentId);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "update status to complete for returning asset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated"),
            @ApiResponse(responseCode = "400", description = "Can get id of returning asset"),
            @ApiResponse(responseCode = "400", description = "Some field not correct")
    })
    public ResponseEntity<Object> updateStatusReturnAsset(@PathVariable int id) {
        return this.returnService.updateStatusReturning(id);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "cancel request for returning")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cancel success"),
            @ApiResponse(responseCode = "400", description = "Not found returning"),
            @ApiResponse(responseCode = "403", description = "Returning is disable")
    })
    public ResponseEntity<Object> deleteRequestReturnAsset(@PathVariable int id) {
        return this.returnService.deleteReturning(id);
    }
    
    @GetMapping
    public List<ReturningResponse> getAllReturningAsset() {
        return this.returnService.getAllReturning();
    }
    
    @GetMapping("/search/{content}")
    public List<ReturningResponse> searchReturning(@PathVariable String content) {
        return this.returnService.search( content);
    }
    
    @Operation(summary = "update state for returning")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update returning successfully"),
            @ApiResponse(responseCode = "404", description = "not found returning"),
            @ApiResponse(responseCode = "403", description = "access denied , wrong role"),
            @ApiResponse(responseCode = "500", description = "logic checked errors")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ReturningResponse> updateStateReturning(@PathVariable long id) throws ParseException {
        ReturningResponse returningResponse = this.returnService.updateStateReturning(id);
        return ResponseEntity.ok().body(returningResponse);
    }
    
}
