package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentStaffResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ForbiddenException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.repository.AssetRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.AssignmentRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
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
		AssignmentStaffResponse assignmentResponse = MappingData.mapping(assignment, AssignmentStaffResponse.class);

		assignmentResponse.setAssetName(assignment.getAsset().getName());
		assignmentResponse.setAssetCode(assignment.getAsset().getId());
		assignmentResponse.setSpecification(assignment.getAsset().getSpecification());
		assignmentResponse.setAssignBy(assignment.getCreator().getUserName());
		assignmentResponse.setAssignTo(assignment.getUser().getUserName());
		return assignmentResponse;
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
	public AssignmentDetailResponse createAssignment(AssignmentRequest assignmentRequest, String id) {
		Assignment assignment = MappingData.mapping(assignmentRequest, Assignment.class);

		User creator = userRepository.findByUserNameAndStatus(id, "ACTIVE")
				.orElseThrow(() -> new ResourceCheckException("Not found username: " + id));

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
		AssignmentDetailResponse assignmentDetailResponse;

		assignmentDetailResponse = MappingData.mapping(saveAssignment, AssignmentDetailResponse.class);
		asset.setState("Assigned");
		assetRepository.save(asset);

		return assignmentDetailResponse;
	}

	@Override
	public AssignmentDetailResponse editAssignment(AssignmentRequest assignmentRequest, Long id) {
		Assignment assignment = MappingData.mapping(assignmentRequest, Assignment.class);

		Assignment oldAssignment = assignmentRepository.findById(id)
				.orElseThrow(() -> new ResourceCheckException("Assignment not found " + assignmentRequest.getAsset()));

		if (!oldAssignment.isStatus()) {
			throw new ForbiddenException("Assignment already disable");
		}

		Asset asset = assetRepository.findById(assignmentRequest.getAsset())
				.orElseThrow(() -> new ResourceCheckException("Asset not found " + assignmentRequest.getAsset()));

		User user = userRepository.findById(assignmentRequest.getUser())
				.orElseThrow(() -> new ResourceCheckException("User not found " + assignmentRequest.getAsset()));

		assignment = MappingData.mapping(oldAssignment, Assignment.class);
		assignment.setAsset(asset);
		assignment.setUser(user);

		Assignment saveAssignment = assignmentRepository.save(assignment);

		AssignmentDetailResponse AssignmentDetailResponse = MappingData.mapping(saveAssignment,
				AssignmentDetailResponse.class);

		return AssignmentDetailResponse;
	}

	@Override
	public ResponseEntity<?> deleteAssignment(Long id) {
		Assignment assignment = assignmentRepository.findById(id)
				.orElseThrow(() -> new ResourceCheckException("Not found assignment id :" + id));
		if (assignment.isStatus() == false) {
			throw new ForbiddenException("Assignment already disable");
		}
		if (!assignment.getState().equalsIgnoreCase("Waiting for acceptance")
				&& !assignment.getState().equalsIgnoreCase("Declined")) {
			throw new ForbiddenException("Assignment cannot disable");
		}
		if (assignment.isStatus() == false) {
			throw new ForbiddenException("Assignment already disable");
		}
		assignment.setStatus(false);
		assignment.getAsset().setState("Available");
		this.assignmentRepository.save(assignment);
		return ResponseEntity.ok().body("Assignment is disabled");

	}

	@Override
	public List<AssignmentDetailResponse> getAllAssignmentByLocation(String location) {
		List<Assignment> assignment = assignmentRepository.getAllAssignmentByLocation(location);
		if (assignment.isEmpty()) {
			throw new ResourceCheckException("No asset found in this location");
		}
		return assignment.stream().map(AssignmentDetailResponse::buildFromAssignment).collect(Collectors.toList());
	}

	@Override
	public List<AssignmentDetailResponse> searchAssignment(String content, String location) {
		List<Assignment> assignment = assignmentRepository.searchUser(content, location);
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
	public List<AssetResponse> getAllAssetByLocationAndState(String location) {
		List<Asset> assets = assetRepository.findAssetByLocationAndState(location);
		if (assets.isEmpty()) {
			throw new ResourceNotFoundException("No asset found in this location");
		}
		return assets.stream().map(AssetResponse::build).collect(Collectors.toList());
	}

	@Override
	public List<AssetResponse> searchAssetByLocationAndState(String location, String content) {
		List<Asset> assets = assetRepository.searchAssetByLocationAndState(location, content);
		if (assets.isEmpty()) {
			throw new ResourceNotFoundException("No asset found in this location");
		}
		return assets.stream().map(AssetResponse::build).collect(Collectors.toList());
	}
}
