package org.education.network.security.services;

import lombok.RequiredArgsConstructor;
import org.education.network.db.model.dto.UserDto;
import org.education.network.db.service.UserService;
import org.education.network.security.model.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogSignService {

    private final UserService userService;
    private final JsonServices json;
    public ResponseEntity login(String request) {

        CommonResponse response = (CommonResponse) json.getObject(request, CommonResponse.class);

        return ResponseEntity.ok(response);

    }

    public ResponseEntity<?> registerUser(UserDto signUp) {

        if(userService.existsByEmail(signUp.getEmail())){
            return new ResponseEntity<>("Email is already exist!", HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(signUp);

        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }
}
