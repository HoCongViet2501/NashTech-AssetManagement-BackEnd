package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import com.nashtech.rookies.java05.AssetManagement.dto.request.ChangePasswordRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.InformationResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ForbiddenException;
import com.nashtech.rookies.java05.AssetManagement.exception.PasswordException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckDateException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Role;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.model.enums.UserStatus;
import com.nashtech.rookies.java05.AssetManagement.repository.AssignmentRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.RoleRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.security.UserPrincipal;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    InformationRepository informationRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    AssignmentRepository assignmentRepository;
    
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public UserServiceImpl(UserRepository userRepository, InformationRepository informationRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.informationRepository = informationRepository;
        this.roleRepository = roleRepository;
    }
    
    public String getLocalUserName() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(userName);
        
        if (userName != null) {
            return userName;
        }
        throw new ResourceNotFoundException("Cannot recognize user. Maybe you haven't log in");
    }
    
    public static int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        if (birthDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
    
    public static int calculateAgeJoinedDate(LocalDate birthDate, LocalDate joinedDate) {
        if ((birthDate != null) && (joinedDate != null)) {
            return Period.between(birthDate, joinedDate).getYears();
        } else {
            return 0;
        }
    }
    
    public void checkDate(SignupRequest signupRequest) {
        int age = calculateAge(signupRequest.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        int ageJoinedDate = calculateAgeJoinedDate(
                signupRequest.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                signupRequest.getJoinedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (age < 18) {
            throw new ResourceCheckDateException("User is under 18. Please select a different date");
        }
        
        if (signupRequest.getJoinedDate().before(signupRequest.getDateOfBirth())) {
            throw new ResourceCheckDateException(
                    "Joined date is not later than Date of Birth. Please select a different date");
        }
        
        if (signupRequest.getJoinedDate().equals(signupRequest.getDateOfBirth())) {
            throw new ResourceCheckDateException(
                    "Joined date is not later than Date of Birth. Please select a different date");
        }
        if (ageJoinedDate < 16) {
            throw new ResourceCheckDateException("User is underage to join. Please select a different date");
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(signupRequest.getJoinedDate());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            throw new ResourceCheckDateException("Joined date is Saturday or Sunday. Please select a different date");
        }
    }
    
    private void encryptPassword(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
    }
    
    public static String removeSpace(String s) {
        return s.trim().replaceAll("\\s+", " ");
    }
    
    
    @Override
    public UserResponse createUser(SignupRequest signupRequest) {
        Information information = MappingData.mapping(signupRequest, Information.class);
        User user = MappingData.mapping(signupRequest, User.class);
        
        checkDate(signupRequest);
        
        // auto create username
        information.setFirstName(information.getFirstName().replaceAll(" ", ""));
        user.setUserName(information.getFirstName().toLowerCase());
        information.setLastName(removeSpace(information.getLastName()));
        user.setUserName(removeAccent(information.getFirstName()).toLowerCase());
        user.setUserName(removeSpace(user.getUserName()));
        
        StringBuilder template = new StringBuilder(user.getUserName());
        
        for (String s : information.getLastName().split(" ")) {
            template.append(s.toLowerCase().charAt(0));
        }
        
        String finalUsername = null;
        boolean flag = true;
        int idx = 0;
        while (flag) {
            // check username in db
            Optional<User> existUser = userRepository.findByUserName(idx == 0 ? template.toString() : template.toString() + idx);
            if (existUser.isPresent()) {
                idx++;
                continue;
            }
            finalUsername = idx == 0 ? template.toString() : template.toString() + idx;
            flag = false;
        }
        user.setUserName(finalUsername);
        
        // Auto create PassWord
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
        user.setPassWord(user.getUserName() + "@" + simpleDateFormat.format(information.getDateOfBirth()));
        encryptPassword(user);
        
        Role role = roleRepository.findById(signupRequest.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Not.found.role"));
        user.setRole(role);
        
        String locations = signupRequest.getLocation();
        if (signupRequest.getRole() == 2) {
            String userName = getLocalUserName();
            locations = informationRepository.getLocationByUserName(userName);
        }
        information.setLocation(locations);
        
        user.setStatus(UserStatus.NEW);
        
        User saveUser = userRepository.save(user);
        information.setUser(saveUser);
        Information saveInformation = informationRepository.save(information);
        InformationResponse informationResponse = MappingData.mapping(saveInformation, InformationResponse.class);
        
        UserResponse userResponse;
        userResponse = MappingData.mapping(saveUser, UserResponse.class);
        userResponse.setInformationResponse(informationResponse);
        return userResponse;
    }
    
    private static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
    
    @Override
    public List<UserDetailResponse> getAllUserSameLocation() {
        List<Information> lists = this.informationRepository.findUserByLocationAndNotInactive(getUserLocationFromSecurity());
        if (lists.isEmpty()) {
            throw new ResourceNotFoundException("No User Founded");
        }
        return lists.stream().map(UserDetailResponse::buildFromInfo).collect(Collectors.toList());
    }
    
    public List<UserDetailResponse> getUserInformationById(String id) {
        List<Information> lists = this.informationRepository.findInformationByUserId(id);
        if (lists.isEmpty()) {
            throw new ResourceNotFoundException("No User Founded");
        }
        return lists.stream().map(UserDetailResponse::buildEditFromInfo).collect(Collectors.toList());
    }
    
    @Override
    public List<UserDetailResponse> searchUser(String content) {
        List<Information> lists = this.informationRepository.searchUser(content, getUserLocationFromSecurity());
        if (lists.isEmpty()) {
            throw new ResourceNotFoundException("No User Founded");
        }
        
        List<UserDetailResponse> result = new ArrayList<>();
        for (Information info : lists) {
            if (!info.getUser().getStatus().equals(UserStatus.INACTIVE)) {
                result.add(UserDetailResponse.buildFromInfo(info));
            }
        }
        
        return result;
    }
    
    @Override
    public boolean checkUserIsAvailable(String staffCode) {
        User user = this.userRepository.findById(staffCode)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        
        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new ForbiddenException("User already disable");
        }
        
        List<Assignment> assignments = this.assignmentRepository.findByUserAndStatus(user, true);
        
        return assignments.isEmpty();
    }
    
    @Override
    public ResponseEntity<Object> disableUser(String staffCode) {
        User user = this.userRepository.findById(staffCode)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        
        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new ForbiddenException("User already disable");
        }
        
        user.setStatus(UserStatus.INACTIVE);
        
        this.userRepository.save(user);
        return ResponseEntity.ok().body("User is disabled");
    }
    
    @Override
    public UserResponse editUserInformation(String id, SignupRequest signupRequest) {
        Information information = MappingData.mapping(signupRequest, Information.class);
        User user = MappingData.mapping(signupRequest, User.class);
        
        checkDate(signupRequest);
        
        User userOptional = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cant find user with id: " + id));
        
        if (userOptional.getStatus() == UserStatus.INACTIVE) {
            throw new ForbiddenException("User has already disabled");
        }
        
        user.setId(id);
        Optional<Information> informationOptional = informationRepository.findByUserId(id);
        information.setId(informationOptional.get().getId());
        user.setUserName(userOptional.getUserName());
        user.setPassWord(userOptional.getPassWord());
        user.setStatus(userOptional.getStatus());
        Role role = roleRepository.findById(signupRequest.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Not.found.role"));
        user.setRole(role);
        information.setDateOfBirth(signupRequest.getDateOfBirth());
        information.setJoinedDate(signupRequest.getJoinedDate());
        information.setGender(signupRequest.isGender());
        
        User saveUser = userRepository.save(user);
        information.setUser(saveUser);
        Information saveInformation = informationRepository.save(information);
        InformationResponse informationResponse = MappingData.mapping(saveInformation, InformationResponse.class);
        
        UserResponse userResponse;
        userResponse = MappingData.mapping(saveUser, UserResponse.class);
        userResponse.setInformationResponse(informationResponse);
        return userResponse;
    }
    
    
    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        String userId = changePasswordRequest.getUserId();
        User user = this.userRepository.findUserById(userId).orElseThrow(
                () -> new ResourceNotFoundException("not.found.user.have.id." + userId));
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassWord())) {
            throw new PasswordException("old.password.incorrect!");
        }
        String newPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPassWord(newPassword);
        this.userRepository.save(user);
    }
    
    public String getUserLocationFromSecurity() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findUserById(userPrincipal.getId()).orElseThrow(
                () -> new ResourceNotFoundException("not.found.user.have.id." + userPrincipal.getId()));
        return user.getInformation().getLocation();
    }
}
