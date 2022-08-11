package com.nashtech.rookies.java05.AssetManagement.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.response.ReturningResponse;

@Service
public interface ReturnService {
    ResponseEntity<Object> createNewReturningAsset(int assignmentId);
    
    ResponseEntity<Object> updateStatusReturning(int returnId);
    
    ResponseEntity<Object> deleteReturning(int returnId);
    
    List<ReturningResponse> getAllReturning();
    
    List<ReturningResponse> search( String content);
    
    ReturningResponse updateStateReturning(long returnId) throws ParseException;
    
}
