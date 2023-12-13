package org.education.network.security.controller;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.CommonResponse;
import org.education.network.dto.UserDto;
import org.education.network.service.controllerService.JwtControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class JwtController {

    private final JwtControllerService jwtControllerService;

    @PostMapping("/accessToken")
    public ResponseEntity getAccessToken(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(
                CommonResponse.builder()
                        .hasErrors(true)
                        .body(jwtControllerService.updateAccess(userDto))
                        .createdAt(Instant.now().toString())
                        .build()
        );
    }

    @PostMapping ("/refreshToken")
    public ResponseEntity getRefreshToken(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(
                CommonResponse.builder()
                        .hasErrors(true)
                        .body(jwtControllerService.updateRefresh(userDto))
                        .createdAt(Instant.now().toString())
                        .build()
        );
    }

}
