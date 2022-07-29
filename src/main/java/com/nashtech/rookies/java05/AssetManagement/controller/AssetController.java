package com.nashtech.rookies.java05.AssetManagement.controller;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssetRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.service.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    AssetResponse createAsset(@Valid @RequestBody AssetRequest assetRequest){
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
    AssetResponse updateAsset(@Valid @RequestBody AssetRequest assetRequest, @PathVariable String id){
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
    Boolean deleteAsset(@PathVariable String id){
        return this.assetService.deleteAsset(id);
    }
}
