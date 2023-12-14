package org.education.network.security.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.service.controllerService.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profile/")
@RequiredArgsConstructor
@Tag(name = "ProfileController", description = "")
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("/edit")
    public ResponseEntity editProfile(@RequestBody UserProfileDto profile) {
        return profileService.editUserProfile(profile);
    }

    @GetMapping("/{email}")
    public ResponseEntity getProfileEmail(@PathVariable("email") String email) {
        return profileService.getUserProfile(email);
    }

}
