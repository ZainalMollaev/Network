package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.UserProfileDto;
import org.education.network.mapping.UserProfileMapper;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserProfileMapper profileMapper;

    public void deleteAvatar(String email){
        profileRepository.deleteAvatarByEmail(email);
    }

    public void deleteBackPhoto(String email){
        profileRepository.deleteBackPhotoByEmail(email);
    }

    public void updateAvatar(String email, UUID avatarId) {
        profileRepository.updateAvatarByEmail(email, avatarId);
    }

    public void saveUserProfile(UserProfileDto user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserProfile userAndProfile = profileMapper.toEntity(user);
        profileRepository.save(userAndProfile);
    }

    public void updateBackPhoto(String email, UUID avatarId) {
        profileRepository.updateAvatarByEmail(email, avatarId);
    }

    public String getPhoto(String email) {
        return profileRepository.findPhotoIdByEmail(email);
    }
}
