package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.SignupRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.InformationResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.UserDetailResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckDateException;
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

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UserServiceImpl(UserRepository userRepository2, InformationRepository informationRepository2,
			RoleRepository roleRepository2) {
		this.userRepository = userRepository2;
		this.informationRepository = informationRepository2;
		this.roleRepository = roleRepository2;
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
		if ((birthDate != null) && (currentDate != null)) {
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
		Information information = MappingData.mapToEntity(signupRequest, Information.class);
		User user = MappingData.mapToEntity(signupRequest, User.class);

		checkDate(signupRequest);

		// auto create username
		user.setUserName(information.getFirstName().toLowerCase());
		information.setFirstName(removeSpace(information.getFirstName()));
		System.out.print("------------" + information.getFirstName());
		information.setLastName(removeSpace(information.getLastName()));
		user.setUserName(removeAccent(information.getFirstName()).toLowerCase());
		user.setUserName(removeSpace(user.getUserName()));

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
		InformationResponse informationResponse = MappingData.mapToEntity(saveInformation, InformationResponse.class);

		UserResponse userResponse;
		userResponse = MappingData.mapToEntity(saveUser, UserResponse.class);
		userResponse.setInformationResponse(informationResponse);
		return userResponse;
	}

	private static String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	@Override
	public List<UserDetailResponse> getAllUserSameLocation(String location) {
		List<Information> lists = this.informationRepository.findByLocation(location);
		if (lists.isEmpty()) {
			throw new ResourceNotFoundException("No User Founded");
		}
		return lists.stream().map(UserDetailResponse::buildFromInfo).collect(Collectors.toList());
	}

	@Override
	public List<UserDetailResponse> searchUser(String content, String location) {
		List<Information> lists = this.informationRepository.searchUser(content, location);
		if (lists.isEmpty()) {
			throw new ResourceNotFoundException("No User Founded");
		}

		return lists.stream().map(UserDetailResponse::buildFromInfo).collect(Collectors.toList());
	}

	@Override
	public UserResponse editUserInformation(String id, SignupRequest signupRequest) {

		Information information = MappingData.mapToEntity(signupRequest, Information.class);
		User user = MappingData.mapToEntity(signupRequest, User.class);

		checkDate(signupRequest);

		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new ResourceNotFoundException("Cant find user with id: " + id);
		}
		
//		User oldUser = userOptional.get();

		user.setId(id);
		information.setId(informationRepository.getInformationIbByUserId(id));
		user.setUserName(userOptional.get().getUserName());
		user.setPassWord(userOptional.get().getPassWord());
		user.setStatus(userOptional.get().getStatus());
//		information.setLastName(userOptional.get().getInformation().getLastName());
//		information.setFirstName(userOptional.get().getInformation().getFirstName());
		Role role = roleRepository.findById(signupRequest.getRole())
				.orElseThrow(() -> new ResourceNotFoundException("Not.found.role"));
		user.setRole(role);
		System.out.println(signupRequest.toString());
		System.out.println(information.toString());
		System.out.println(user.toString());
		information.setDateOfBirth(signupRequest.getDateOfBirth());
		information.setJoinedDate(signupRequest.getJoinedDate());
		information.setGender(signupRequest.isGender());

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
	 * // public String getLocation(String username) { // Information information =
	 * informationRepository.getByUsername(username) // .orElseThrow(() -> new
	 * ResourceNotFoundExceptions("Username not found")); // return
	 * information.getLocation();
	 * 
	 * // }
	 */

}
