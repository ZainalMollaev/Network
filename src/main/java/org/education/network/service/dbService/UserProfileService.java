package org.education.network.service.dbService;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.mapping.UserProfileMapper;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserProfileMapper profileMapper;

    public void saveUserProfile(UserProfileDto user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserProfile userAndProfile = profileMapper.toEntity(user);
        profileRepository.save(userAndProfile);
    }

    public void saveUserProfileByEmail(UserProfileDto userProfileDto) {
        UserProfile userProfile = profileRepository.findByEmail(userProfileDto.getEmail());
        userProfile = profileMapper.partialUpdate(userProfileDto, userProfile);
        profileRepository.save(userProfile);
    }

    public UserProfileDto getUserProfile(String email) {
        UserProfile userProfile = profileRepository.findByEmail(email);
        UserProfileDto userProfileDto = profileMapper.toDto(userProfile);
        return userProfileDto;
    }

}
