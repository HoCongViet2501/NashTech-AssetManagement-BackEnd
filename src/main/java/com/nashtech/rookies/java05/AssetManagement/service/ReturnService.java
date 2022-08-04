package com.nashtech.rookies.java05.AssetManagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.response.ReturningResponse;

@Service
public interface ReturnService {
    public ResponseEntity<Object> createNewReturningAsset(int assId, String requestBy);

    public ResponseEntity<Object> updateStatusReturning(int returnId);

    public ResponseEntity<Object> deleteReturning(int returnId);

    public List<ReturningResponse> getAllReturning(String location);

    public List<ReturningResponse> search(String location,String content);

}
