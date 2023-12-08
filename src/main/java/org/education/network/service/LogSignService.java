package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.CommonResponse;
import org.education.network.dto.UserProfileDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            return new ResponseEntity<>("Email is already exist!", HttpStatus.BAD_REQUEST);
        }

        userProfileService.saveUserProfile(signUp);

        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }
}