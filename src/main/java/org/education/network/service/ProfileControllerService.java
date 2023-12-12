package org.education.network.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.CommonResponse;
import org.education.network.dto.MediaDto;
import org.education.network.security.auth.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileControllerService {

    private final UserProfileService userProfileService;
    private final JwtUtil jwtUtil;
    private final MinioService minioService;

    public ResponseEntity deleteFile(HttpServletRequest req)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String email = jwtUtil.getEmail(req);
        String[] url = req.getRequestURI().split("/");
        String prefix = url[url.length - 1];

        String photoId = userProfileService.getPhoto(email);

        if (prefix.equals("avatar")) {
            userProfileService.deleteAvatar(email);
        } else if (prefix.equals("backImg")) {
            userProfileService.deleteBackPhoto(email);
        }

        minioService.deleteFile(photoId);

        return ResponseEntity.status(204).body(CommonResponse.builder()
                .hasErrors(false)
                .body("Avatar was removed")
                .createdAt(Instant.now().toString())
                .build());
    }

    public ResponseEntity updateFile(HttpServletRequest req,
                                     MultipartFile file)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String email = jwtUtil.getEmail(req);
        String[] url = req.getRequestURI().split("/");
        String prefix = url[url.length - 1];

        UUID photoId = UUID.randomUUID();

        if (prefix.equals("avatar")) {
            userProfileService.updateAvatar(email, photoId);
        } else if (prefix.equals("backPhoto")) {
            userProfileService.updateBackPhoto(email, photoId);
        }

        minioService.uploadFile(
                photoId,
                file.getInputStream());

        return ResponseEntity.ok().body(CommonResponse.builder()
                .hasErrors(false)
                .body("Avatar was updated")
                .createdAt(Instant.now().toString())
                .build()
        );
    }

    public ResponseEntity getFileId(HttpServletRequest req) {
        String email = jwtUtil.getEmail(req);
        String photoId = userProfileService.getPhoto(email);

        return ResponseEntity.ok().body(CommonResponse.builder()
                .hasErrors(false)
                .body(
                        MediaDto.builder()
                                .photoId(photoId)
                                .build()
                )
                .createdAt(Instant.now().toString())
                .build()
        );
    }
}