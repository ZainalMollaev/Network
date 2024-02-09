package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.service.LogSignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "Registration and Authorization")
public class AuthController {

    private final LogSignService logSignService;
    @Operation(
            summary = "Authorization/Login",
            description = "check if access token is right")
    @PostMapping(value = "/login")
    public ResponseEntity login(HttpServletRequest request) {
        return logSignService.login((String) request.getAttribute("loginRes"));
    }

    @Operation(
            summary = "Registration/Signup",
            description = "save login and credentials")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserProfileDto signUp) {
        return logSignService.registerUser(signUp);
    }

}
