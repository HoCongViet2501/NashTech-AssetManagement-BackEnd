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
    
    @PostMapping("/{assId}/{requestBy}")
    @Operation(summary = "Create new request for returning asset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create new request for returning asset successfully"),
            @ApiResponse(responseCode = "400", description = "Some field not correct"),
    })
    public ResponseEntity<Object> createRequestReturningAsset(@PathVariable int assId, @PathVariable String requestBy) {
        return this.returnService.createNewReturningAsset(assId, requestBy);
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
    
    @Operation(summary = "get list asset of returning by location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get list returning successfully"),
            @ApiResponse(responseCode = "404", description = "not found asset of returning"),
            @ApiResponse(responseCode = "403", description = "access denied , wrong role"),
            @ApiResponse(responseCode = "400", description = "bad request,Some field not correct")
    })
    @GetMapping("/{location}")
    public List<ReturningResponse> getAllReturningAsset(@PathVariable String location) {
        return this.returnService.getAllReturning(location);
    }
    
    @Operation(summary = "searching for returning by location and content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "founded returning successfully"),
            @ApiResponse(responseCode = "404", description = "not found returning"),
            @ApiResponse(responseCode = "403", description = "access denied , wrong role"),
            @ApiResponse(responseCode = "400", description = "bad request,Some field not correct")
    })
    @GetMapping("/search/{location}/{content}")
    public List<ReturningResponse> searchReturning(@PathVariable String location, @PathVariable String content) {
        return this.returnService.search(location, content);
    }
    
    @Operation(summary = "update state for returning")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update returning successfully"),
            @ApiResponse(responseCode = "404", description = "not found returning"),
            @ApiResponse(responseCode = "403", description = "access denied , wrong role"),
            @ApiResponse(responseCode = "400", description = "bad request,Some field not correct")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ReturningResponse> updateStateReturning(@PathVariable long id) throws ParseException {
        ReturningResponse returningResponse = this.returnService.updateStateReturning(id);
        return ResponseEntity.ok().body(returningResponse);
    }
    
}
