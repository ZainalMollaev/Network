package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileService userProfileService;

    public String editUserProfile(UserProfileDto userProfileDto, String subject) {
        userProfileDto.setEmail(subject);
        userProfileService.editUserProfileByEmail(userProfileDto);
        return "Changes saved";
    }

    public UserProfileDto getUserProfile(String email) {
        return userProfileService.getUserProfileDto(email);
    }



}
