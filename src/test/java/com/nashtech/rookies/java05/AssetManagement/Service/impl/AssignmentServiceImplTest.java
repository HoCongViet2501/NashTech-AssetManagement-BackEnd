//package com.nashtech.rookies.java05.AssetManagement.service.impl;
//
//import com.nashtech.rookies.java05.AssetManagement.dto.request.AssignmentRequest;
//import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentResponse;
//import com.nashtech.rookies.java05.AssetManagement.dto.response.AssignmentStaffResponse;
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
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.ResponseEntity;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.mockito.Mockito.*;
//
//public class AssignmentServiceImplTest {
//    
//    AssignmentRepository assignmentRepository;
//    
//    UserRepository userRepository;
//    
//    InformationRepository informationRepository;
//    
//    AssetRepository assetRepository;
//    
//    AssignmentServiceImpl assignmentServiceImpl;
//    
//    AssignmentRequest assignmentRequest;
//    
//    User user;
//    
//    User creator;
//    
//    Asset asset;
//    Assignment assignment;
//    Information information;
//    
//    ModelMapper modelMapper;
//    
//    MappingData mappingData;
//    
//    AssignmentResponse assignmentResponse;
//    
//    @BeforeEach
//    public void beforeEach() {
//        assignmentRepository = mock(AssignmentRepository.class);
//        userRepository = mock(UserRepository.class);
//        informationRepository = mock(InformationRepository.class);
//        assetRepository = mock(AssetRepository.class);
//        modelMapper = spy(ModelMapper.class);
//        mappingData = new MappingData(modelMapper);
//        assignmentRepository = mock(AssignmentRepository.class);
//        modelMapper = spy(ModelMapper.class);
//        assignmentServiceImpl = new AssignmentServiceImpl(assignmentRepository, userRepository, assetRepository, informationRepository);
//        assignmentRequest = mock(AssignmentRequest.class);
//        
//        assignment = mock(Assignment.class);
//        user = mock(User.class);
//        creator = mock(User.class);
//        asset = mock(Asset.class);
//        information = mock(Information.class);
//        assignmentResponse = mock(AssignmentResponse.class);
//    }
//    
//    @Test
//    public void createAssignment_ShouldReturnAssignment_WhenDataValid() throws ParseException {
//        String username = "adminHCM";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        assignmentRequest.setAsset("LP000008");
//        assignmentRequest.setUser("blomasney4");
//        assignmentRequest.setAssignedDate(formatter.parse("2022-8-9"));
//        assignmentRequest.setNote("Ram 16GB");
//        assignmentRequest.setState("Waiting for acceptance");
//        assignmentRequest.setStatus(true);
//        
//        
//    }
//    
//    @Test
//    public void deleteAssignment() throws ParseException {
//        Long id = 1L;
//        when(assignmentRepository.findById(id)).thenReturn(Optional.of(assignment));
//        
//        
//        if (assignment.isStatus() == false) {
//            throw new ForbiddenException("Assignment already disable");
//        }
//        if (!assignment.getState().equalsIgnoreCase("Waiting for acceptance")
//                && !assignment.getState().equalsIgnoreCase("Declined")) {
//            throw new ForbiddenException("Assignment cannot disable");
//        } else {
//            verify(assignment).setStatus(false);
////			assignment.setStatus(false);
////			assignment.getAsset().setState("Available");
//            verify(assignment).getAsset().setState("Available");
////			this.assignmentRepository.save(assignment);
//            verify(assignmentRepository).save(assignment);
//            
//            assertThat("Assignment is disabled", is(ResponseEntity.ok()));
////			return ResponseEntity.ok().body("Assignment is disabled");
//        }
//    }
//    
//    @Test
//    public void givenUserId_thenReturnListAssignment_whenUserFound() {
//        Assignment assignment1 = mock(Assignment.class);
//        AssignmentStaffResponse assignmentStaffResponse1 = mock(AssignmentStaffResponse.class);
//        
//        when(assignmentRepository.getAssignmentsByIdAndAssignedDateBeforeNow("SD0001"))
//                .thenReturn(List.of(assignment1));
//        when(assignmentServiceImpl.getListAssignments("SD0001")).thenReturn(List.of(assignmentStaffResponse1));
//    }
//    
//    @Test
//    public void givenStateAndId_shouldUpdatedAssignmentState_thenReturnNewAssignment() {
//        Assignment assignment1 = mock(Assignment.class);
//        
//        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment1));
//        assignment1.setState("Declined");
//        verify(assignment1).setState("Declined");
//        when(assignmentRepository.save(assignment1)).thenReturn(assignment1);
//        when(assignmentServiceImpl.updateStateAssignment(1L, "Declined")).thenReturn(any());
//        ArgumentCaptor<AssignmentStaffResponse> captor = ArgumentCaptor.forClass(AssignmentStaffResponse.class);
//    }
//    
//}
//
