package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;

import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;

public interface AssetService {

  public List<AssetResponse> getAllAssetByLocation(String location);

  public List<AssetResponse> searchAsset(String content, String location);
}
