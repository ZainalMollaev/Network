package org.education.network.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.mapping.UserProfileMapper;
import org.education.network.web.exceptions.EmailExistException;
import org.education.network.web.exceptions.WrongJsonException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogSignService {

    private final UserService userService;
    private final UserProfileService profileService;
    private final UserProfileMapper profileMapper;
    private final ObjectMapper mapper;

    public CommonResponse login(String request) {
        CommonResponse response;
        try {
            response = mapper.readValue(request, CommonResponse.class);
        } catch (JsonProcessingException e) {
            throw new WrongJsonException(e);
        }
        return response;
    }

    public UserProfileDto registerUser(UserProfileDto signUp) {

        if(userService.existsByEmail(signUp.getEmail())){
            throw new EmailExistException("");
        }

        return profileMapper.toDto(profileService.saveUserProfile(signUp));

    }
}
