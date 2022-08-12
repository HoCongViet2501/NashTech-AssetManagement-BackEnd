package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.nashtech.rookies.java05.AssetManagement.exception.InvalidException;
import com.nashtech.rookies.java05.AssetManagement.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.response.ReturningResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ForbiddenException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Returning;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.repository.AssignmentRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.ReturnRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.service.ReturnService;

@Service
public class ReturnServiceImpl implements ReturnService {
    
    @Autowired
    private ReturnRepository returnRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Override
    public ResponseEntity<Object> createNewReturningAsset(int assignmentId) {
        String userName = getUserFromSecurity().getUsername();
        User requestUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("Username " + userName + " Not Founded"));
        Assignment assignment = assignmentRepository.findById((long) assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        
        Returning newReturn = Returning.builder().isDelete(false).requestBy(requestUser).assignment(assignment)
                .state("Waiting for returning").build();
        if (assignment.isHasReturning()) {
            throw new ForbiddenException("Assignment has been a request for returning");
        }
        
        assignment.setHasReturning(true);
        assignmentRepository.save(assignment);
        this.returnRepository.save(newReturn);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @Override
    public ResponseEntity<Object> updateStatusReturning(int returnId) {
    	
        return null;
    }
    
    @Override
    public ResponseEntity<Object> deleteReturning(int returnId) {
        Returning returning = this.returnRepository.findById((long) returnId).orElseThrow(() ->
                new ResourceCheckException("Not Found Returning"));

         if (returning.isDelete()){
             throw new ForbiddenException("Returning is disable");
         }
         if ("Completed".equals(returning.getState())){
             throw new ForbiddenException("Returning is completed");
         }
        Assignment assignment = this.assignmentRepository.getById(returning.getAssignment().getId());
        assignment.setHasReturning(false);
        assignmentRepository.save(assignment);
        
        returning.setDelete(true);
        returnRepository.save(returning);
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @Override
    public List<ReturningResponse> getAllReturning() {
        List<Returning> returnLists = this.returnRepository.getAllReturning(getUserLocationFromSecurity());
        
        if (returnLists.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No request for returning found in location: " + getUserLocationFromSecurity());
        }
        
        return returnLists.stream().map(ReturningResponse::buildFromModel).collect(Collectors.toList());
    }
    
    @Override
    public List<ReturningResponse> search(String content) {
        List<Returning> returnLists = this.returnRepository.search(getUserLocationFromSecurity(), content);
        
        if (returnLists.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No request for returning found in location: " + getUserLocationFromSecurity() + " With " + content);
        }
        
        return returnLists.stream().map(ReturningResponse::buildFromModel).collect(Collectors.toList());
    }
    
    @Override
    public ReturningResponse updateStateReturning(long returnId) throws ParseException {
        Returning returning = this.returnRepository.findById(returnId).orElseThrow(
                () -> new ResourceNotFoundException("not.found.returning.have.id." + returnId));
        
        if (returning.getState().equals("Completed")) {
            throw new InvalidException("this.returning.has.been.completed");
        } else if (returning.isDelete()) {
            throw new InvalidException("this.returning.have.status.deleted");
        }
        
        UserPrincipal userPrincipal = getUserFromSecurity();
        User acceptedBy = this.userRepository.findUserById(userPrincipal.getId()).orElseThrow(
                () -> new ResourceNotFoundException("not.found.user.have.id." + userPrincipal.getId()));
        
        returning.setState("Completed");
        returning.setAcceptedBy(acceptedBy);
        returning.setReturnedDate(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString()));
        returning.getAssignment().setStatus(false);
        returning.getAssignment().getAsset().setState("Available");
        this.returnRepository.save(returning);

        return ReturningResponse.buildFromModel(returning);
    }
    
    public UserPrincipal getUserFromSecurity() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public String getUserLocationFromSecurity() {
        User user = this.userRepository.findUserById(getUserFromSecurity().getId()).orElseThrow(
                () -> new ResourceNotFoundException("not.found.user.have.id." + getUserFromSecurity().getId()));
        return user.getInformation().getLocation();
    }
}
