package org.education.network.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.education.network.security.auth.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class ProfileControllerService {

    private final UserProfileService userProfileService;
    private final JwtUtil jwtUtil;
    private final MinioService minioService;

    public ResponseEntity deleteAvatar(HttpServletRequest req)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String email = jwtUtil.getEmail(req);
        minioService.deleteAvatar(email);
        userProfileService.deleteAvatar(jwtUtil.getEmail(req));
        return ResponseEntity.status(204).build();
    }

    public ResponseEntity updateAvatar(HttpServletRequest req,
                                       MultipartFile file)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String email = jwtUtil.getEmail(req);
        minioService.uploadAvatar(email, file.getInputStream());
        userProfileService.updateAvatar(email);
        return ResponseEntity.ok().build();
    }
}
