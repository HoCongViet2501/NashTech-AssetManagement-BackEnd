package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.repository.AssetRepository;
import com.nashtech.rookies.java05.AssetManagement.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

  @Autowired
  private AssetRepository assetRepository;

  @Override
  public List<AssetResponse> getAllAssetByLocation(String location) {
    List<Asset> assets = assetRepository.findAssetByLocation(location);
    if (assets.isEmpty()) {
      throw new ResourceNotFoundException("No asset found in this location");
    }
    return assets.stream().map(AssetResponse::build).collect(Collectors.toList());
  }
  
  @Override
  public List<AssetResponse> searchAsset(String content, String location) {
    
    List<Asset> assets = assetRepository.searchAssetByNameOrCode(content, location);
    if (assets.isEmpty()) {
      throw new ResourceNotFoundException("No asset found in this location");
    }
    return assets.stream().map(AssetResponse::build).collect(Collectors.toList());
  }

}
