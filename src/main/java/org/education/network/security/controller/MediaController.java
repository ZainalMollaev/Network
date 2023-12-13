package org.education.network.security.controller;

import lombok.RequiredArgsConstructor;
import org.education.network.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @GetMapping("/getImage")
    public ResponseEntity getImage(@RequestParam("photoId") String photoId)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return mediaService.mediaService(photoId);
    }

}
