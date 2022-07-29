package com.nashtech.rookies.java05.AssetManagement.service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssetRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AssetService {
  AssetResponse createAsset(AssetRequest assetRequest);

  public Authentication getAuthentication();

  AssetResponse updateAsset(AssetRequest assetRequest, String id);

  boolean deleteAsset(String id);

  public List<AssetResponse> getAllAssetByLocation(String location);

  public List<AssetResponse> searchAsset(String content, String location);
}
