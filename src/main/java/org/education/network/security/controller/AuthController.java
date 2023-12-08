package org.education.network.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.education.network.dto.UserDto;
import org.education.network.service.LogSignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/network/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final LogSignService logSignService;

    @PostMapping(value = "/login")
    public ResponseEntity login(HttpServletRequest request) {
        return logSignService.login((String) request.getAttribute("loginRes"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto signUp) {
        return logSignService.registerUser(signUp);
    }

}