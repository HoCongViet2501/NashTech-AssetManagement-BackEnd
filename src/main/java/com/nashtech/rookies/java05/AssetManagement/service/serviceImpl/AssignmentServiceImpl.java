package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentStaffResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ForbiddenException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.repository.AssetRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.AssignmentRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.security.UserPrincipal;
import com.nashtech.rookies.java05.AssetManagement.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InformationRepository informationRepository;

    @Autowired
    AssetRepository assetRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository2, UserRepository userRepository2,
            AssetRepository assetRepository2, InformationRepository informationRepository2) {
        this.assignmentRepository = assignmentRepository2;
        this.userRepository = userRepository2;
        this.informationRepository = informationRepository2;
        this.assetRepository = assetRepository2;
    }

    @Override
    public List<AssignmentStaffResponse> getListAssignments(String userId) {
        List<Assignment> assignments = this.assignmentRepository.getAssignmentsByIdAndAssignedDateBeforeNow(userId);
        if (assignments.isEmpty()) {
            throw new ResourceNotFoundException("no.assignment.found");
        }
        return assignments.stream().map(AssignmentStaffResponse::build).collect(Collectors.toList());
    }

    @Override
    public AssignmentStaffResponse getAssignmentById(long id) {
        Assignment assignment = this.assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not.found.assignment.have.id." + id));
        return AssignmentStaffResponse.build(assignment);
    }

    @Override
    public AssignmentStaffResponse updateStateAssignment(long id, String state) {
        Assignment assignment = this.assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not.found.assignment.have.id." + id));
        assignment.setState(state);
        this.assignmentRepository.save(assignment);
        return AssignmentStaffResponse.build(assignment);
    }

    @Override
    public AssignmentDetailResponse createAssignment(AssignmentRequest assignmentRequest) {
        Assignment assignment = MappingData.mapping(assignmentRequest, Assignment.class);

        User creator = userRepository.findByUserNameAndStatus(getUserFromSecurity().getId(), "ACTIVE")
                .orElseThrow(() -> new ResourceCheckException("Not found username: " + getUserFromSecurity().getUsername()));

        User user = userRepository.findByUserNameAndStatus(assignmentRequest.getUser(), "ACTIVE")
                .orElseThrow(() -> new ResourceCheckException("Not found username: " + assignmentRequest.getUser()));

        Asset asset = assetRepository.findByIdAndState(assignmentRequest.getAsset(), "Available")
                .orElseThrow(() -> new ResourceCheckException("Not found asset: " + assignmentRequest.getAsset()));

        assignment.setUser(user);
        assignment.setCreator(creator);
        assignment.setAsset(asset);
        assignment.setState("Waiting for acceptance");
        assignment.setHasReturning(false);
        assignment.setStatus(true);
        Assignment saveAssignment = assignmentRepository.save(assignment);
        asset.setState("Assigned");
        assetRepository.save(asset);

        return AssignmentDetailResponse.buildFromAssignment(saveAssignment);
    }

    @Override
    public AssignmentDetailResponse editAssignment(AssignmentRequest assignmentRequest, Long id) {
        Assignment oldAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceCheckException("Assignment not found "));

        if (!oldAssignment.isStatus()) {
            throw new ForbiddenException("Assignment already disable");
        }
        
        Asset oldAsset = assetRepository.findById(oldAssignment.getAsset().getId())
                .orElseThrow(() -> new ResourceCheckException("Asset not found " + assignmentRequest.getAsset()));
        Asset asset = assetRepository.findById(assignmentRequest.getAsset())
                .orElseThrow(() -> new ResourceCheckException("Asset not found " + assignmentRequest.getAsset()));
        User user = userRepository.findById(assignmentRequest.getUser())
                .orElseThrow(() -> new ResourceCheckException("User not found " + assignmentRequest.getUser()));

        Assignment assignment = MappingData.mapping(oldAssignment, Assignment.class);
        assignment.setAsset(asset);
        assignment.setUser(user);
        assignment.setStatus(oldAssignment.isStatus());
        assignment.setAssignedDate(assignmentRequest.getAssignedDate());
        Assignment saveAssignment = assignmentRepository.save(assignment);

        oldAsset.setState("Available");
        assetRepository.save(oldAsset);

        asset.setState("Assigned");
        assetRepository.save(asset);

        return AssignmentDetailResponse.buildFromAssignment(saveAssignment);
    }

    @Override
    public ResponseEntity<?> deleteAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceCheckException("Not found assignment id :" + id));
        if (!assignment.isStatus()) {
            throw new ForbiddenException("Assignment already disable");
        }
        if (!assignment.getState().equalsIgnoreCase("Waiting for acceptance")
                && !assignment.getState().equalsIgnoreCase("Declined")) {
            throw new ForbiddenException("Assignment cannot disable");
        }
        assignment.setStatus(false);
        assignment.getAsset().setState("Available");
        this.assignmentRepository.save(assignment);
        return ResponseEntity.ok().body("Assignment is disabled");

    }

    @Override
    public List<AssignmentDetailResponse> getAllAssignmentByLocation() {
        List<Assignment> assignment = assignmentRepository.getAllAssignmentByLocation(getUserLocationFromSecurity());
        if (assignment.isEmpty()) {
            throw new ResourceCheckException("No asset found in this location");
        }
        return assignment.stream().map(AssignmentDetailResponse::buildFromAssignment).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDetailResponse> searchAssignment(String content) {
        List<Assignment> assignment = assignmentRepository.searchUser(content, getUserLocationFromSecurity());
        if (assignment.isEmpty()) {
            throw new ResourceCheckException("No asset found in this location");
        }
        return assignment.stream().map(AssignmentDetailResponse::buildFromAssignment).collect(Collectors.toList());
    }

    @Override
    public AssignmentDetailResponse getAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceCheckException("Not found assignment id :" + id));
        return AssignmentDetailResponse.buildFromAssignment(assignment);
    }

    @Override
    public List<AssetResponse> getAllAssetByLocationAndState() {
        List<Asset> assets = assetRepository.findAssetByLocationAndState(getUserLocationFromSecurity());
        if (assets.isEmpty()) {
            throw new ResourceNotFoundException("No asset found in this location");
        }
        return assets.stream().map(AssetResponse::build).collect(Collectors.toList());
    }

    @Override
    public List<AssetResponse> searchAssetByLocationAndState(String content) {
        List<Asset> assets = assetRepository.searchAssetByLocationAndState(getUserLocationFromSecurity(), content);
        if (assets.isEmpty()) {
            throw new ResourceNotFoundException("No asset found in this location");
        }
        return assets.stream().map(AssetResponse::build).collect(Collectors.toList());
    }
    
    @Override
    public List<UserDetailResponse> getAllUserByLocationAndStatus() {
        List<Information> lists = informationRepository.findUserByLocationAndStatus(getUserLocationFromSecurity());
        if (lists.isEmpty()) {
            throw new ResourceNotFoundException("No User Founded");
        }
        return lists.stream().map(UserDetailResponse::buildFromInfo).collect(Collectors.toList());
    }
    
    @Override
    public List<UserDetailResponse> searchUserByLocationAndStatus(String content) {
        List<Information> lists = this.informationRepository.searchUserAndStatus(content, getUserLocationFromSecurity());
        if (lists.isEmpty()) {
            throw new ResourceNotFoundException("No User Founded");
        }
        return lists.stream().map(UserDetailResponse::buildFromInfo).collect(Collectors.toList());
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
