package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.UserProfileDto;
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

    public void deleteAvatar(String email){
        profileRepository.deleteAvatarByEmail(email);
    }

    public void updateAvatar(String email) {
        profileRepository.updateAvatarByEmail(email, "avatar/"+email);
    }

    public void saveUserProfile(UserProfileDto user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserProfile userAndProfile = profileMapper.toEntity(user);

        profileRepository.save(userAndProfile);
    }

}
