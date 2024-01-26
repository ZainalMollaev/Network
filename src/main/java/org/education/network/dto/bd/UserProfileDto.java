package org.education.network.dto.bd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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
    private String title;
    private String company;
    private String specialization;
    private String university;
    private String location;
    private List<String> languages;
    private String avatar;
    private String backPhoto;
    private String phoneNumber;

}
