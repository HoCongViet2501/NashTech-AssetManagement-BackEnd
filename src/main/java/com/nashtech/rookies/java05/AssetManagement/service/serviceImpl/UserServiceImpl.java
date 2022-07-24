package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.response.InformationResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckDateExceptions;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.mapper.MappingData;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Information;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Role;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.model.enums.UserStatus;
import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.RoleRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	InformationRepository informationRepository;

	@Autowired
	RoleRepository roleRepository;

	public static int calculateAge(LocalDate birthDate) {
		LocalDate currentDate = LocalDate.now();
		if ((birthDate != null) && (currentDate != null)) {
			return Period.between(birthDate, currentDate).getYears();
		} else {
			return 0;
		}
	}

	@Override
	public void checkDate(SignupRequest signupRequest) {
		int age = calculateAge(signupRequest.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		if (age < 18) {
			throw new ResourceCheckDateExceptions("User is under 18. Please select a different date");
		}

		if (signupRequest.getJoinedDate().before(signupRequest.getDateOfBirth())) {
			throw new ResourceCheckDateExceptions("Joined date is not later than Date of Birth. Please select a different date");
		}

		if (signupRequest.getJoinedDate().equals(signupRequest.getDateOfBirth())) {
			throw new ResourceCheckDateExceptions("Joined date is not later than Date of Birth. Please select a different date");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(signupRequest.getJoinedDate());
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			throw new ResourceCheckDateExceptions("Joined date is Saturday or Sunday. Please select a different date");
		}
	}

	@Override
	public UserResponse createUser(SignupRequest signupRequest) {
		Information information = MappingData.mapToEntity(signupRequest, Information.class);
		User user = MappingData.mapToEntity(signupRequest, User.class);

		checkDate(signupRequest);
		
		user.setId(user.getId());
		

		// auto create username
		user.setUserName(information.getFirstName().toLowerCase());
		
		String template = user.getUserName();

		for (String s : information.getLastName().split(" ")) {
			template += s.toLowerCase().charAt(0);
		}

		String finalUsername = null;
		boolean flag = true;
		int idx = 0;
		while (flag) {
			// check username in db
			Optional<User> existUser = userRepository.findByUserName(idx == 0 ? template : template + idx);
			if (existUser.isPresent()) {
				idx++;
				continue;
			}
			finalUsername = idx == 0 ? template : template + idx;
			flag = false;
		}
		user.setUserName(finalUsername);

		// Auto create PassWord

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
		user.setPassWord(user.getUserName() + "@" +simpleDateFormat.format(information.getDateOfBirth()));

		Role role = roleRepository.findById(signupRequest.getRole())
				.orElseThrow(() -> new ResourceNotFoundException("Not.found.role"));
		user.setRole(role);
		
		user.setStatus(UserStatus.NEW);
		
		User saveUser = userRepository.save(user);
		information.setUser(saveUser);
		Information saveInformation = informationRepository.save(information);
		InformationResponse informationResponse = MappingData.mapToEntity(saveInformation, InformationResponse.class);

		UserResponse userResponse;
		userResponse = MappingData.mapToEntity(saveUser, UserResponse.class);
		userResponse.setInformationResponse(informationResponse);
		return userResponse;
	}
	
	/**
	 * 
	// public String getLocation(String username) {
	// 	Information information = informationRepository.getByUsername(username)
	// 			.orElseThrow(() -> new ResourceNotFoundExceptions("Username not found"));
	// 	return information.getLocation();
		
	// }
	 */

}
