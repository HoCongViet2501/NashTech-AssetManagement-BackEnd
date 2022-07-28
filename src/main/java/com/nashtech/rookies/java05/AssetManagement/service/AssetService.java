package com.nashtech.rookies.java05.AssetManagement.service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssetRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import org.springframework.stereotype.Service;

@Service
public interface AssetService {
    AssetResponse createAsset(AssetRequest assetRequest, String creatorId);
}
