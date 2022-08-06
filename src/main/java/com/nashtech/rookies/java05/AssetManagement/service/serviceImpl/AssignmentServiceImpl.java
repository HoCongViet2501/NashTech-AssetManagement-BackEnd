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
import com.nashtech.rookies.java05.AssetManagement.dto.response.InformationResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ForbiddenException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckException;
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
		assignment.setStatus(true);
		assignmentRepository.save(assignment);
		UserResponse userResponse = MappingData.mapping(user.get(), UserResponse.class);
		userResponse
				.setInformationResponse(MappingData.mapping(user.get().getInformation(), InformationResponse.class));

//		UserResponse userCreateResponse = MappingData.mapping(creator, UserResponse.class);
//		userCreateResponse
//				.setInformationResponse(MappingData.mapping(creator.getInformation(), InformationResponse.class));

		AssignmentResponse assignmentResponse = new AssignmentResponse();
		
		
		assignmentResponse.setId(assignment.getId());
		assignmentResponse.setAssetResponse(MappingData.mapping(asset.get(), AssetResponse.class));
		
		assignmentResponse.setCreateUser(MappingData.mapping(assignmentOptional.get().getCreator(), UserResponse.class));
		assignmentResponse.getCreateUser().setInformationResponse(MappingData.mapping(assignmentOptional.get().getCreator().getInformation(), InformationResponse.class));

		assignmentResponse.setUser(userResponse);

//		assignmentResponse.setCreateUser(userCreateResponse);

		assignmentResponse.setAssignedDate(assignment.getAssignedDate());
		assignmentResponse.setState(assignment.getState());
		assignmentResponse.setNote(assignment.getNote());

		return assignmentResponse;
	}

// 	@Override
// 	public AssignmentResponse getAssignment(Long id) {
// 		Optional<Assignment> assignmentOptional = assignmentRepository.findById(id);
// 		System.out.println(assignmentOptional.toString());
// 		if(assignmentOptional.isEmpty()) {
// 			throw new ResourceCheckException("Cant find assignment with id: " + id);
// 		}
// 		AssignmentResponse assignmentResponse = new AssignmentResponse();
// 		assignmentResponse.setId(assignmentOptional.get().getId());
// 		assignmentResponse.setAssetResponse(MappingData.mapping(assignmentOptional.get().getAsset(), AssetResponse.class));
// 		assignmentResponse.setUser(MappingData.mapping(assignmentOptional.get().getUser(), UserResponse.class));
// 		assignmentResponse.getUser().setInformationResponse(MappingData.mapping(assignmentOptional.get().getUser().getInformation(), InformationResponse.class));
// 		assignmentResponse.setCreateUser(MappingData.mapping(assignmentOptional.get().getCreator(), UserResponse.class));
// 		assignmentResponse.getCreateUser().setInformationResponse(MappingData.mapping(assignmentOptional.get().getCreator().getInformation(), InformationResponse.class));
// 		assignmentResponse.setState(assignmentOptional.get().getState());
// 		assignmentResponse.setAssignedDate(assignmentOptional.get().getAssignedDate());
// 		assignmentResponse.setNote(assignmentOptional.get().getNote());
// //		assignmentResponse.setStatus(assignmentOptional.get().isStatus());


		
// //		AssignmentResponse assignmentResponse =  MappingData.mapping(assignmentOptional.get(), AssignmentResponse.class);
// 		return assignmentResponse;
// 	}

	@Override
	public ResponseEntity<?> deleteAssignment(Long id) {
		Assignment assignment = assignmentRepository.findById(id)
				.orElseThrow(() -> new ResourceCheckException("Not found assignment id :" + id));
		if (assignment.isStatus() == false) {
			throw new ForbiddenException("Assignment already disable");
		} 
		if (!assignment.getState().equalsIgnoreCase("Waiting for acceptance")) {
			throw new ForbiddenException("Assignment cannot disable");
		} 
		else {
			assignment.setStatus(false);
			this.assignmentRepository.save(assignment);
			return ResponseEntity.ok().body("Assignment is disabled");
		}

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
		// TODO Auto-generated method stub
		Assignment assignment = assignmentRepository.findById(id)
				.orElseThrow(() -> new ResourceCheckException("Not found assignment id :" + id));
		return AssignmentDetailResponse.buildFromAssignment(assignment);
	}

}
