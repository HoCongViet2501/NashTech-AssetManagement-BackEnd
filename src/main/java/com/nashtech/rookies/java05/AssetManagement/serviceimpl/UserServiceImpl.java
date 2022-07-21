package com.nashtech.rookies.java05.AssetManagement.serviceimpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.nashtech.rookies.java05.AssetManagement.Model.DTO.InformationDTO;
import com.nashtech.rookies.java05.AssetManagement.Model.DTO.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Information;
import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Role;
import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Users;
import com.nashtech.rookies.java05.AssetManagement.Model.enums.UStatus;
import com.nashtech.rookies.java05.AssetManagement.exception.InvalidException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundExceptions;
import com.nashtech.rookies.java05.AssetManagement.repository.InformationRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.RoleRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.response.MessageResponse;
import com.nashtech.rookies.java05.AssetManagement.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	InformationRepository informationRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ModelMapper modelMapper;

//	@Autowired
//    PasswordEncoder encoder;

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
			throw new InvalidException("User is under 18. Please select a different date");
		}

		if (signupRequest.getJoinedDate().before(signupRequest.getDateOfBirth())) {
			throw new InvalidException("Joined date is not later than Date of Birth. Please select a different date");
		}

		if (signupRequest.getJoinedDate().equals(signupRequest.getDateOfBirth())) {
			throw new InvalidException("Joined date is not later than Date of Birth. Please select a different date");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(signupRequest.getJoinedDate());
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			throw new InvalidException("Joined date is Saturday or Sunday. Please select a different date");
		}
	}

	@Override
	public UserResponse createUser(SignupRequest signupRequest) {
		Users users = modelMapper.map(signupRequest, Users.class);
		Information information = modelMapper.map(signupRequest, Information.class);
		checkDate(signupRequest);
	
		// auto create username
		String template = users.getUsername();

		for (String s : information.getLastname().split(" ")) {
			template += s.toLowerCase().charAt(0);
		}

		String finalUsername = null;
		boolean flag = true;
		int idx = 0;
		while (flag) {
			// check username in db
			Optional<Users> existUser = userRepository.findByUsername(idx == 0 ? template : template + idx);
			if (existUser.isPresent()) {
				idx++;
				continue;
			}
			finalUsername = idx == 0 ? template : template + idx;
			flag = false;
		}
		users.setUsername(finalUsername);

		// Auto create PassWord

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
		users.setPassword(users.getUsername() + "@" + simpleDateFormat.format(information.getDateOfBirth().toString()));
		
		users.setStatus(UStatus.NEW_USER);
		
		Users saveUser = userRepository.save(users);
		Information saveInformation = informationRepository.save(information);
		
		UserResponse userResponse;
		userResponse = modelMapper.map(saveUser,UserResponse.class);
		userResponse = modelMapper.map(saveInformation, UserResponse.class);
		
		return userResponse;
	}

}
