package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.service.JwtService;
import org.education.network.util.ResponseEntityUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity getAccessToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntityUtil.get(HttpStatus.OK, jwtService.updateAccess(token));
    }

    @Operation(
            summary = "get refresh token",
            description = "generate refresh token and save it")
    @PostMapping ("/refreshToken")
    public ResponseEntity getRefreshToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntityUtil.get(HttpStatus.OK, jwtService.updateRefresh(token));
    }

}
