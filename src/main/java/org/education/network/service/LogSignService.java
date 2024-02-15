package org.education.network.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.util.ResponseEntityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogSignService {

    private final UserService userService;
    private final UserProfileService profileService;
    private final ObjectMapper mapper;

    public ResponseEntity login(String request) {
        CommonResponse response;
        try {
            response = mapper.readValue(request, CommonResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntityUtil.get(HttpStatus.OK, response);
    }

    public ResponseEntity<?> registerUser(UserProfileDto signUp) {

        if(userService.existsByEmail(signUp.getEmail())){
            return ResponseEntityUtil.get(HttpStatus.BAD_REQUEST, "Email is already exist!");
        }

        profileService.saveUserProfile(signUp);

        return ResponseEntityUtil.get(HttpStatus.OK, "User is registered successfully!");

    }
}
