package com.nashtech.rookies.java05.AssetManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiResponse(responseCode = "400", description = "Can get id of returning asset")
    })
    public ResponseEntity<Object> updateStatusReturnAsset(@PathVariable int id) {
        return this.returnService.updateStatusReturning(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRequestReturnAsset(@PathVariable int id) {
        return this.returnService.deleteReturning(id);
    }

    @GetMapping("/{location}")
    public List<ReturningResponse> getAllReturningAsset(@PathVariable String location) {
        return this.returnService.getAllReturning(location);
    }

    @GetMapping("/search/{location}/{content}")
    public List<ReturningResponse> searchReturning(@PathVariable String location, @PathVariable String content) {
        return this.returnService.search(location, content);
    }
    
    @PostMapping("/{assId}/{requestBy}")
    @Operation(summary = "Create new request for returning asset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create new request for returning asset successfully"),
            @ApiResponse(responseCode = "400", description = "Some field not correct"),
    })
    public ResponseEntity<Object> createUserRequestReturningAsset(@PathVariable int assId, @PathVariable String requestBy) {
        return this.returnService.createNewReturningAsset(assId, requestBy);
    }

}
