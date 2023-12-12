package org.education.network.service.controllerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.CommonResponse;
import org.education.network.security.auth.JwtUtil;
import org.education.network.service.dbService.MinioService;
import org.education.network.service.profile.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileControllerService {

    private final List<UserProfileService> profileItems;
    private final MinioService minioService;

    public ResponseEntity deleteFile(String email,
                                     String photoId,
                                     String method)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        profileItems
                .stream()
                .filter(i -> i.support(method))
                .findFirst()
                .get()
                .delete(email);

        minioService.deleteFile(photoId);

        return ResponseEntity.status(204).body(CommonResponse.builder()
                .hasErrors(false)
                .body("Avatar was removed")
                .createdAt(Instant.now().toString())
                .build());
    }

    public ResponseEntity updateFile(String email,
                                     MultipartFile file,
                                     String method)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        String photoId = UUID.randomUUID().toString();

        profileItems
                .stream()
                .filter(i -> i.support(method))
                .findFirst()
                .get()
                .update(email, photoId);

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

    public ResponseEntity getFileId(String email, String method) {

        String photoId = profileItems
                .stream()
                .filter(i -> i.support(method))
                .findFirst()
                .get()
                .getPhoto(email);

        return ResponseEntity.ok().body(CommonResponse.builder()
                .hasErrors(false)
                .body(photoId)
                .createdAt(Instant.now().toString())
                .build()
        );
    }
}