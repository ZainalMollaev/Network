package org.education.network.service.profile;

import lombok.RequiredArgsConstructor;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AvatarService extends UserProfileService {

    private final UserProfileRepository profileRepository;

    public void delete(String email){
        profileRepository.deleteAvatarByEmail(email);
    }

    public void update(String email, String avatarId) {
        profileRepository.updateAvatarByEmail(email, UUID.fromString(avatarId));
    }

    @Override
    public boolean support(String method) {
        return method.equals("avatar");
    }

    @Override
    public String getPhoto(String email) {
        return profileRepository.findAvatarIdByEmail(email);
    }

}
