package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.service.dbService.MinioService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MinioService minioService;

    public ResponseEntity mediaService(String photoId)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(minioService.getFile(photoId).readAllBytes());
    }

}
