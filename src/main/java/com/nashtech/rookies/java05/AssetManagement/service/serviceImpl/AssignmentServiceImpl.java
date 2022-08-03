package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.repository.AssignmentRepository;
import com.nashtech.rookies.java05.AssetManagement.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    
    private final AssignmentRepository assignmentRepository;
    
    @Autowired
    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }
    
    
    @Override
    public List<AssignmentDetailResponse> getListAssignments() {
        List<Assignment> assignments = this.assignmentRepository.getAssignmentsByAssignedDateBeforeNow();
        return assignments.stream().map(AssignmentDetailResponse::build).collect(Collectors.toList());
    }
    
    @Override
    public AssignmentDetailResponse getAssignmentById(long id) {
        Assignment assignment = this.assignmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("not.found.assignment.have.id." + id));
        AssignmentDetailResponse assignmentResponse = MappingData.mapping(assignment, AssignmentDetailResponse.class);
        assignmentResponse.setAssetName(assignment.getAsset().getName());
        assignmentResponse.setAssetCode(assignment.getAsset().getId());
        assignmentResponse.setSpecification(assignment.getAsset().getSpecification());
        assignmentResponse.setAssignBy(assignment.getCreator().getUserName());
        assignmentResponse.setAssignTo(assignment.getUser().getUserName());
        return assignmentResponse;
    }
}
