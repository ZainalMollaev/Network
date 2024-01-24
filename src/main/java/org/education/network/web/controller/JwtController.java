package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.response.CommonResponse;
import org.education.network.dto.bd.UserDto;
import org.education.network.service.JwtService;
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

    private final JwtService jwtService;

    @Operation(
            summary = "get access token",
            description = "generate access token and get")
    @PostMapping("/accessToken")
    public ResponseEntity getAccessToken(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(
                CommonResponse.builder()
                        .hasErrors(true)
                        .body(jwtService.updateAccess(userDto))
                        .createdAt(Instant.now().toString())
                        .build()
        );
    }

    @Operation(
            summary = "get refresh token",
            description = "generate refresh token and save it")
    @PostMapping ("/refreshToken")
    public ResponseEntity getRefreshToken(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(
                CommonResponse.builder()
                        .hasErrors(true)
                        .body(jwtService.updateRefresh(userDto))
                        .createdAt(Instant.now().toString())
                        .build()
        );
    }

}
