package org.education.network.security.controller;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.CommonResponse;
import org.education.network.dto.UserDto;
import org.education.network.service.JwtControllerService;
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

    private final JwtControllerService jcs;

    @PostMapping("/accessToken")
    public ResponseEntity getAccessToken(@RequestBody UserDto userDto){
        return ResponseEntity.ok(
                CommonResponse.builder()
                        .hasErrors(true)
                        .body(jcs.updateAccess(userDto))
                        .createdAt(Instant.now().toString())
                        .build()
        );
    }

    @PostMapping ("/refreshToken")
    public ResponseEntity getRefreshToken(@RequestBody UserDto userDto){
        return ResponseEntity.ok(
                CommonResponse.builder()
                        .hasErrors(true)
                        .body(jcs.updateRefresh(userDto))
                        .createdAt(Instant.now().toString())
                        .build()
        );
    }

}
