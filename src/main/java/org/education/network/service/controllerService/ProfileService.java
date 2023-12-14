package org.education.network.service.controllerService;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.service.dbService.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileService userProfileService;

    public ResponseEntity editUserProfile(UserProfileDto userProfileDto) {
        userProfileService.saveUserProfileByEmail(userProfileDto);
        return ResponseEntity.ok(CommonResponse.builder()
                        .hasErrors(false)
                        .body("Changes saved")
                .build());
    }

    public ResponseEntity getUserProfile(String email) {
        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body(userProfileService.getUserProfile(email))
                .build());
    }
}
