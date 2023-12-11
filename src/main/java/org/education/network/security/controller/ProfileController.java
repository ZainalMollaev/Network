package org.education.network.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.education.network.service.ProfileControllerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile/")
@Slf4j
public class ProfileController {

    private final ProfileControllerService pcs;

    @PutMapping("/delete/avatar")
    public ResponseEntity delete(HttpServletRequest req)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return pcs.deleteAvatar(req);
    }

    @PutMapping(value = "/update/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity update(@RequestParam("file") MultipartFile file,
                                 HttpServletRequest req)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return pcs.updateAvatar(req, file);
    }
}