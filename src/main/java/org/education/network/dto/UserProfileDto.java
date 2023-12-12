package org.education.network.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserProfileDto extends UserDto{

    private String name;
    private String lastname;
    private String birthDate;
    private boolean gender;
    private String title;
    private String company;
    private String specialization;
    private String university;
    private String location;
    private String avatar;
    private String backPhoto;
    private String phoneNumber;

}
