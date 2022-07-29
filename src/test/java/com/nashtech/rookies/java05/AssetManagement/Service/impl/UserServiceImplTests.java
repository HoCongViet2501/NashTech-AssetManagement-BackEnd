// package com.nashtech.rookies.java05.AssetManagement.Service.impl;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.when;

// import java.sql.Date;
// import java.util.ArrayList;
// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.nashtech.rookies.java05.AssetManagement.dto.response.AllUserResponse;
// import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
// import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
// import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
// import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
// import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.UserServiceImpl;

// @ExtendWith(MockitoExtension.class)
// public class UserServiceImplTests {
//   @Mock
//   private InformationRepository informationRepository;

//   @Mock
//   private Information information;

//   @Mock
//   private List<Information> lInformations = new ArrayList<>();

//   @Mock
//   private UserDetailResponse userDetailResponse;

//   @Mock
//   private AllUserResponse allUserResponse;

//   @InjectMocks
//   private UserServiceImpl userServiceImpl;

//   @Test
//   public void GivenLocationAndRaw_ShouldReturnList_WhenLocationValid() {
//     // 1. create mock data
//     information = new Information(1L, "user1", "last1", Date.valueOf("2000-02-20"), true, "HCM",
//         Date.valueOf("2022-02-20"), null);
//     lInformations.add(information);

//     // 2. define behavior of Repository
//     when(informationRepository.findAllUserSameLocation("HCM", 0)).thenReturn(lInformations);
//     when(informationRepository.findTotalUserSameLocation("HCM")).thenReturn(1);

//     // 3. call service method
//     AllUserResponse actual = userServiceImpl.getAllUserResponse("HCM", 1);

//     // 4. assert the result
//     assertThat(actual.getRaw()).isEqualTo(1);
//   }

//   @Test
//   public void GivenLocation_ThenResourceNotFoundException_WhenLocationInvalid() {
//     // 1. create mock data
//     information = new Information(1L, "user1", "last1", Date.valueOf("2000-02-20"), true, "HCM",
//         Date.valueOf("2022-02-20"), null);
//     lInformations.add(information);

//     // 4. assert the result
//     assertThrows(ResourceNotFoundException.class, () -> {
//       // 3. call service method
//       userServiceImpl.getAllUserResponse("DN", 1);
//     });
//   }
// }
