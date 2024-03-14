package org.education.network.dto.bd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.education.network.enumtypes.Privacy;
import org.education.network.enumtypes.Role;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class UserProfileDto extends UserDto{

    private String name;
    private String lastname;
    private String birthDate;
    private String gender;
    private Privacy birthPrivacy;
    private String title;
    private String company;
    private Privacy jobPrivacy;
    private String specialization;
    private String university;
    private Privacy educationPrivacy;
    private String location;
    private Privacy locationPrivacy;
    private List<String> languages;
    private String avatar;
    private String backPhoto;
    private String phoneNumber;
    private List<Role> roles;

}
