package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.ReturnAssetRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.ReturningResponse;
import com.nashtech.rookies.java05.AssetManagement.service.ReturnService;

@Service
public class ReturnServiceImpl implements ReturnService {

    @Override
    public ResponseEntity<Object> createNewReturningAsset(ReturnAssetRequest dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Object> updateStatusReturning(int returnId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteReturning(int returnId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ReturningResponse> getAllReturning() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
