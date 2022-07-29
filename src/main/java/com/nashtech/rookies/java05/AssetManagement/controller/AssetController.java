package com.nashtech.rookies.java05.AssetManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.service.AssetService;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

  @Autowired
  private AssetService assetService;

  @GetMapping("/getAll/{location}")
  public List<AssetResponse> getAllAsset(@PathVariable String location) {
    return assetService.getAllAssetByLocation(location);
  }

  @GetMapping("/search/{location}/{content}")
  public List<AssetResponse> searchAsset(@PathVariable String location,@PathVariable String content) {
    return assetService.searchAsset(content, location);
  }
}
