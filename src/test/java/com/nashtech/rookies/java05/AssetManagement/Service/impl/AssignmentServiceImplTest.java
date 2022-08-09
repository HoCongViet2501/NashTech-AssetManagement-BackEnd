//package com.nashtech.rookies.java05.AssetManagement.Service.impl;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.verify;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.mockito.Mockito.when;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.ResponseEntity;
//
//import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
//import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentResponse;
//import com.nashtech.rookies.java05.AssetManagement.dto.response.InformationResponse;
//import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
//import com.nashtech.rookies.java05.AssetManagement.exception.ForbiddenException;
//import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
//import com.nashtech.rookies.java05.AssetManagement.repository.AssetRepository;
//import com.nashtech.rookies.java05.AssetManagement.repository.AssignmentRepository;
//import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
//import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
//import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.AssignmentServiceImpl;
//
//public class AssignmentServiceImplTest {
//
//	AssignmentRepository assignmentRepository;
//	
//	UserRepository userRepository;
//
//	InformationRepository informationRepository;
//
//	AssetRepository assetRepository;
//	
//	AssignmentServiceImpl assignmentServiceImpl;
//	AssignmentRequest assignmentRequest;
//	User user;
//	User creator;
//	
//	Asset asset;
//	Assignment assignment;
//	Information information;
//	
//    ModelMapper modelMapper;
//    
//    MappingData mappingData;
//    
//    AssignmentResponse assignmentResponse;
//    
//    @BeforeEach
//    public void veforeEach() {
//    	assignmentRepository = mock(AssignmentRepository.class);
//    	userRepository = mock(UserRepository.class);
//    	informationRepository = mock(InformationRepository.class);
//    	assetRepository = mock(AssetRepository.class);
//    	modelMapper= spy(ModelMapper.class);
//    	mappingData = new MappingData(modelMapper);
//    	
//    	assignmentServiceImpl = new AssignmentServiceImpl(assignmentRepository,userRepository,assetRepository,informationRepository);
//    	assignmentRequest = mock(AssignmentRequest.class);
//    	
//    	assignment = mock(Assignment.class);
//    	user = mock(User.class);
//    	creator = mock(User.class);
//    	asset = mock(Asset.class);
//        information = mock(Information.class);
//        assignmentResponse = mock(AssignmentResponse.class);
//    }
//    
//    @Test
//    public void createAssignment_ShouldReturnAssignment_WhenDataValid() throws ParseException {
//    	String username ="adminHCM";
//   		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//   		assignmentRequest.setAsset("LP000008");
//   		assignmentRequest.setUser("blomasney4");
//   		assignmentRequest.setAssignedDate(formatter.parse("2022-8-9"));
//   		assignmentRequest.setNote("Ram 16GB");
//   		assignmentRequest.setState("Waiting for acceptance");
//   		assignmentRequest.setStatus(true);
//    	
//    	
//    }
//    
//    @Test
//    public void deleteAssignment() throws ParseException {
//    	Long id = 1L;
//    	when(assignmentRepository.findById(id)).thenReturn(Optional.of(assignment));
//    	
//    	
//    	if (assignment.isStatus() == false) {
//			throw new ForbiddenException("Assignment already disable");
//		}
//		if (!assignment.getState().equalsIgnoreCase("Waiting for acceptance")
//				&& !assignment.getState().equalsIgnoreCase("Declined")) {
//			throw new ForbiddenException("Assignment cannot disable");
//		} else {
//			verify(assignment).setStatus(false);
////			assignment.setStatus(false);
////			assignment.getAsset().setState("Available");
//			verify(assignment).getAsset().setState("Available");
////			this.assignmentRepository.save(assignment);
//			verify(assignmentRepository).save(assignment);
//
//			assertThat("Assignment is disabled", is(ResponseEntity.ok()));
////			return ResponseEntity.ok().body("Assignment is disabled");
//		}
//    }
// 
//}
