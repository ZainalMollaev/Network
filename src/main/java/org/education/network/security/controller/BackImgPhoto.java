package org.education.network.security.controller;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.MediaDto;
import org.education.network.service.controllerService.ProfileControllerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class BackImgPhoto {

    private final ProfileControllerService pcs;

    @DeleteMapping("/backImg")
    public ResponseEntity deleteAvatar(@RequestBody MediaDto mediaDto)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return pcs.deleteFile(
                mediaDto.getEmail(),
                mediaDto.getPhotoId(),
                "avatar");
    }

    @PutMapping(value = "/backImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateAvatar(@ModelAttribute MediaDto mediaDto)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return pcs.updateFile(
                mediaDto.getEmail(),
                mediaDto.getFile(),
                "avatar");
    }

    @GetMapping(value = "/backImg")
    public ResponseEntity get(@RequestBody MediaDto mediaDto) {
        return pcs.getFileId(
                mediaDto.getEmail(),
                "avatar");
    }

}
