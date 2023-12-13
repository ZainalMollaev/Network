package org.education.network.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "JwtController", description = "Operations with jwt")
public class JwtController {

    private final JwtControllerService jwtControllerService;

    @Operation(
            summary = "get access token",
            description = "generate access token")
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

    @Operation(
            summary = "get refresh token",
            description = "generate refresh token and save it to postgres users")
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
