package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentStaffResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.InformationResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
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
	public AssignmentResponse createAssignment(AssignmentRequest assignmentRequest, String username) {
		Assignment assignment = MappingData.mapping(assignmentRequest, Assignment.class);

		User creator = userRepository.findByUserNameAndStatus(username, "ACTIVE")
				.orElseThrow(() -> new ResourceCheckException("Not found username: " + username));

		User user = userRepository.findByUserNameAndStatus(assignmentRequest.getUser(), "ACTIVE")
				.orElseThrow(() -> new ResourceCheckException("Not found username: " + assignmentRequest.getUser()));

		Asset asset = assetRepository.findByIdAndState(assignmentRequest.getAsset(), "Available")
				.orElseThrow(() -> new ResourceCheckException("Not found asset: " + assignmentRequest.getAsset()));

		assignment.setCreator(creator);
		assignment.setUser(user);
		assignment.setAsset(asset);
		assignment.setState("Waiting for acceptance");
		assignment.setHasReturning(false);
		assignment.setStatus(true);
		assignmentRepository.save(assignment);

		UserResponse userResponse = MappingData.mapping(user, UserResponse.class);
		userResponse.setInformationResponse(MappingData.mapping(user.getInformation(), InformationResponse.class));

		UserResponse userCreateResponse = MappingData.mapping(creator, UserResponse.class);
		userCreateResponse
				.setInformationResponse(MappingData.mapping(creator.getInformation(), InformationResponse.class));

		AssignmentResponse assignmentResponse = new AssignmentResponse();
		assignmentResponse.setId(assignment.getId());
		assignmentResponse.setAssetResponse(MappingData.mapping(asset, AssetResponse.class));

		assignmentResponse.setUser(userResponse);

		assignmentResponse.setCreateUser(userCreateResponse);

		assignmentResponse.setAssignedDate(assignment.getAssignedDate());
		assignmentResponse.setState(assignment.getState());
		assignmentResponse.setNote(assignment.getNote());

		asset.setState("Not available");
		assetRepository.save(asset);

		return assignmentResponse;
	}

	@Override
	public AssignmentResponse editAssignment(AssignmentRequest assignmentRequest, Long id) {
		Assignment assignment = MappingData.mapping(assignmentRequest, Assignment.class);

		Optional<Assignment> assignmentOptional = assignmentRepository.findById(id);
		if (!assignmentOptional.isPresent()) {
			throw new ResourceCheckException("Not found assignment");
		}
		Optional<Asset> asset = assetRepository.findById(assignmentRequest.getAsset());
		if (!asset.isPresent()) {
			throw new ResourceCheckException("Asset not found " + assignmentRequest.getAsset());
		}

		Optional<User> user = userRepository.findById(assignmentRequest.getUser());
		
		if (!user.isPresent()) {
			throw new ResourceCheckException("User not found ");
		}

		assignment.setId(assignmentOptional.get().getId());
		assignment.setState(assignmentOptional.get().getState());
		assignment.setCreator(assignmentOptional.get().getCreator());
		assignment.setAsset(asset.get());
		assignment.setUser(user.get());
		assignment.setAssignedDate(assignmentRequest.getAssignedDate());
		assignment.setNote(assignmentRequest.getNote());
		assignment.setStatus(assignmentOptional.get().isStatus());
		assignmentRepository.save(assignment);
		
		UserResponse userResponse = MappingData.mapping(user.get(), UserResponse.class);
		userResponse
				.setInformationResponse(MappingData.mapping(user.get().getInformation(), InformationResponse.class));

		AssignmentResponse assignmentResponse = new AssignmentResponse();

		assignmentResponse.setId(assignment.getId());
		assignmentResponse.setAssetResponse(MappingData.mapping(asset.get(), AssetResponse.class));

		assignmentResponse
				.setCreateUser(MappingData.mapping(assignmentOptional.get().getCreator(), UserResponse.class));
		assignmentResponse.getCreateUser().setInformationResponse(
				MappingData.mapping(assignmentOptional.get().getCreator().getInformation(), InformationResponse.class));

		assignmentResponse.setUser(userResponse);
		assignmentResponse.setAssignedDate(assignment.getAssignedDate());
		assignmentResponse.setState(assignment.getState());
		assignmentResponse.setNote(assignment.getNote());

		return assignmentResponse;
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
