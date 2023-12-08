package org.education.network.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.UserProfileDto;
import org.education.network.security.auth.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileControllerService {

    private final UserProfileService userProfileService;
    private final JwtUtil jwtUtil;

    public ResponseEntity deleteAvatar(HttpServletRequest req) {
        userProfileService.deleteAvatar(jwtUtil.getEmail(req));
        return ResponseEntity.status(204).build();
    }

    public ResponseEntity updateAvatar(UserProfileDto userDto, HttpServletRequest req) {
        userProfileService.updateAvatar(jwtUtil.getEmail(req), userDto.getAvatar());
        return ResponseEntity.ok().build();
    }
}
