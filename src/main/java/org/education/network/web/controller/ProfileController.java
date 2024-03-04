package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.service.UserProfileService;
import org.education.network.util.ResponseEntityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "ProfileController", description = "operations with profile")
public class ProfileController {

    private final UserProfileService profileService;

    @Operation(summary = "edit profile", description = "edit all fields")
    @PutMapping("/edit")
    public ResponseEntity editProfile(@RequestBody UserProfileDto profile, Principal principal) {
        return ResponseEntityUtil.get(HttpStatus.OK, profileService.editUserProfile(profile, principal.getName()));
    }

    @Operation(summary = "get profile", description = "get all fields")
    @GetMapping
    public ResponseEntity getProfileEmail(Principal principal) {
        return ResponseEntityUtil.get(HttpStatus.OK, profileService.getUserProfileDto(principal.getName()));
    }

}
