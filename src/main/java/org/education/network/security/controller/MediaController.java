package org.education.network.security.controller;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.MediaDto;
import org.education.network.service.dbService.MediaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = "/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity savePicture(@ModelAttribute MediaDto mediaDto)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return mediaService.saveMedia(mediaDto);
    }

    @DeleteMapping(value = "/picture")
    public ResponseEntity deletePicture(@RequestBody MediaDto mediaDto)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return mediaService.deleteMedia(mediaDto);
    }

    @GetMapping(value = "/picture/{email}")
    public ResponseEntity getPictureId(@PathVariable("email") String email) {
        return mediaService.getFileId(email);
    }

    @GetMapping(value = "/picture")
    public ResponseEntity getPicture(@RequestParam("fileId") String fileId)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return mediaService.getFile(fileId);
    }

}
