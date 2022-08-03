package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.ReturnAssetRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.ReturningResponse;

@Service
public interface ReturnService {
    public ResponseEntity<Object> createNewReturningAsset(ReturnAssetRequest dto);

    public ResponseEntity<Object> updateStatusReturning(int returnId);

    public ResponseEntity<Object> deleteReturning(int returnId);

    public List<ReturningResponse> getAllReturning();

}
