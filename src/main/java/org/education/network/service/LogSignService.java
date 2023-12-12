package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.CommonResponse;
import org.education.network.dto.UserProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LogSignService {

    private final UserService userService;
    private final UserProfileService userProfileService;
    private final JsonServices json;

    public ResponseEntity login(String request) {
        CommonResponse response = (CommonResponse) json.getObject(request, CommonResponse.class);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> registerUser(UserProfileDto signUp) {
        if(userService.existsByEmail(signUp.getEmail())){
            return ResponseEntity.badRequest().body(CommonResponse.builder()
                    .hasErrors(false)
                    .body("Email is already exist!")
                    .createdAt(Instant.now().toString())
                    .build());
        }

        userProfileService.saveUserProfile(signUp);

        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body("User is registered successfully!")
                .createdAt(Instant.now().toString())
                .build());
    }
}