package com.nashtech.rookies.java05.AssetManagement.controller;

import java.util.List;

import javax.validation.Valid;

import com.nashtech.rookies.java05.AssetManagement.model.interfaces.AssetHistoryInterface;
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

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssetRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.service.AssetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
    private final AssetService assetService;
    
    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")
    })
    @Operation(summary = "create new asset")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    AssetResponse createAsset(@Valid @RequestBody AssetRequest assetRequest) {
        return this.assetService.createAsset(assetRequest);
    }
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")
    })
    @Operation(summary = "update asset")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    AssetResponse updateAsset(@Valid @RequestBody AssetRequest assetRequest, @PathVariable String id) {
        return this.assetService.updateAsset(assetRequest, id);
    }
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")
    })
    @Operation(summary = "delete asset")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    Boolean deleteAsset(@PathVariable String id) {
        return this.assetService.deleteAsset(id);
    }
    
    
    @GetMapping("/getAll")
    public List<AssetResponse> getAllAsset() {
        return assetService.getAllAssetByLocation();
    }
    
    @GetMapping("/search/{content}")
    public List<AssetResponse> getAllAsset(@PathVariable String content) {
        return assetService.searchAsset(content);
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AssetResponse getEmployeeById(@PathVariable("id") String id) {
        return this.assetService.getAssetById(id);
    }
    
    @GetMapping("/check/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Check asset can disable or not")
    public ResponseEntity<Boolean> checkAssetHistory(@PathVariable String id) {
        return ResponseEntity.ok().body(this.assetService.checkAssetHistory(id));
    }

    @GetMapping("/history/{assetId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get asset history list by Id")
    public List<AssetHistoryInterface> getAssetHistory(@PathVariable String assetId) {
        return this.assetService.getAssetHistory(assetId);
    }
}
