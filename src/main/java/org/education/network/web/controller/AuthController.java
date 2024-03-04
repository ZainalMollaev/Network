package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.dto.response.LoginResDto;
import org.education.network.mapping.UserProfileMapper;
import org.education.network.service.UserProfileService;
import org.education.network.util.ResponseEntityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "Registration and Authorization")
public class AuthController {

    private final UserProfileMapper profileMapper;
    private final UserProfileService profileService;

    @Operation(
            summary = "Authorization/Login",
            description = "check if access token is right",
            parameters = {@Parameter(
                    name = "email",
                    required = true,
                    schema = @Schema(name = "String")
            ), @Parameter(
                    name = "password",
                    required = true,
                    schema = @Schema(name = "String")
            )})
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestAttribute("email") String email,
                                @RequestAttribute("accessToken") String accessToken,
                                @RequestAttribute("refreshToken") String refreshToken) {
        return ResponseEntityUtil.get(HttpStatus.OK,
                LoginResDto.builder()
                        .email(email)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build());
    }

    @Operation(
            summary = "Registration/Signup",
            description = "save login and credentials")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserProfileDto signUp) {
        return ResponseEntityUtil.get(HttpStatus.OK, profileMapper.toDto(profileService.saveUserProfile(signUp)));
    }

}
