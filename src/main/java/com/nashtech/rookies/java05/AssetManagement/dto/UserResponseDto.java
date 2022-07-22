package com.nashtech.rookies.java05.AssetManagement.dto;

import java.util.Date;

import com.nashtech.rookies.java05.AssetManagement.Model.Entity.Role;
import com.nashtech.rookies.java05.AssetManagement.Model.Entity.User;
import com.nashtech.rookies.java05.AssetManagement.Model.enums.UserRole;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserResponseDto {
    private String staffCode;
    private String fullName;
    private String username;
    private Date dateOfBirth;
    private String gender;
    private Date joinedDay;
    private UserRole role;
    private String location;

    /**
     * @param staffCode   id of user
     * @param fullName    = firstName + LastName
     * @param username
     * @param dateOfBirth
     * @param gender
     * @param joinedDay
     * @param role
     * @param location
     */
    public UserResponseDto(String staffCode, String fullName, String username, Date dateOfBirth, String gender,
            Date joinedDay, UserRole role, String location) {
        this.staffCode = staffCode;
        this.fullName = fullName;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.joinedDay = joinedDay;
        this.role = role;
        this.location = location;
    }

    public static UserResponseDto build(User user) {
        String fullName = user.getInformation().getFirstName() + " " + user.getInformation().getLastName();
        String gender = user.getInformation().isGender() ? "Male" : "Female";
        return new UserResponseDto(user.getId(),
                fullName,
                user.getUsername(),
                user.getInformation().getDateOfBirth(),
                gender,
                user.getInformation().getJoinedDate(),
                user.getRole().getName(),
                user.getInformation().getLocation());
    }
}
