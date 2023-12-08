package org.education.network.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.UserProfileDto;
import org.education.network.service.ProfileControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile/")
public class ProfileController {

    private final ProfileControllerService pcs;

    @PutMapping("/delete/avatar")
    public ResponseEntity delete(HttpServletRequest req) {
        return pcs.deleteAvatar(req);
    }

    @PutMapping("/update/avatar")
    public ResponseEntity update(@RequestBody UserProfileDto userDto, HttpServletRequest req) {
        return pcs.updateAvatar(userDto, req);
    }

}