package org.education.network.service.profile;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.UserProfileDto;
import org.education.network.mapping.UserProfileMapper;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonUserProfile {

    private final UserProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserProfileMapper profileMapper;

    public void saveUserProfile(UserProfileDto user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserProfile userAndProfile = profileMapper.toEntity(user);
        profileRepository.save(userAndProfile);
    }

}
