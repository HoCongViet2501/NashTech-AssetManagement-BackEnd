// package com.nashtech.rookies.java05.AssetManagement.Service.impl;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.Mockito.when;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
// import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
// import com.nashtech.rookies.java05.AssetManagement.repository.AssignmentRepository;
// import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
// import com.nashtech.rookies.java05.AssetManagement.service.UserService;

// // @SpringBootTest

// public class DisableUserTest {

//     @Mock
//     private UserService userService;

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private AssignmentRepository assignmentRepository;

//     @Mock
//     private User user;

//     @Mock
//     private Assignment assignment;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void givenId_ShowReturnTrue_whenIdValid() {
//         user = new User();
//         assignment = new Assignment();
//         Optional<User> optionalUser = Optional.of(user);

//         List<Assignment> list = new ArrayList<>();
//         list.add(assignment);

//         when(userRepository.findById("SD001")).thenReturn(optionalUser);
//         when(assignmentRepository.findByUserAndStatus(user, false)).thenReturn(list);

//         boolean expectResult = userService.checkUserIsAvailable("SD001");
//         assertThat(expectResult).isFalse();
//     }

//     @Test
//     public void givenId_ShowReturnTrue_whenIdInvalid() {
//         user = new User();
//         List<Assignment> list = new ArrayList<>();

//         when(userRepository.findById("staffCode")).thenReturn(Optional.of(user));
//         when(assignmentRepository.findByUserAndStatus(user, false)).thenReturn(list);

//         boolean expectResult = userService.checkUserIsAvailable("staffCode");

//         assertThat(expectResult).isTrue();

//     }
// }
