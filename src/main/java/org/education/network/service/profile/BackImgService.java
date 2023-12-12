package org.education.network.service.profile;

import lombok.RequiredArgsConstructor;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BackImgService extends UserProfileService {

    private final UserProfileRepository profileRepository;

    @Override
    public void delete(String email) {
        profileRepository.deleteBackPhotoByEmail(email);
    }

    @Override
    public void update(String email, String backImg) {
        profileRepository.updateAvatarByEmail(email, UUID.fromString(backImg));
    }

    @Override
    public boolean support(String method) {
        return method.equals("backImg");
    }

    @Override
    public String getPhoto(String email) {
        return profileRepository.findBackPhotoIdByEmail(email);
    }

}

