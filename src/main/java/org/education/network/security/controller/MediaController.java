package org.education.network.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Tag(name = "MediaController", description = "CRUD of media")
public class MediaController {

    private final MediaService mediaService;

    @Operation(
            summary = "save picture",
            description = "Save picture to minio and postgres")
    @PostMapping(value = "/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity savePicture(@ModelAttribute MediaDto mediaDto) {
        return mediaService.saveMedia(mediaDto);
    }

    @Operation(
            summary = "delete picture",
            description = "delete picture from minio and postgres")
    @DeleteMapping(value = "/picture")
    public ResponseEntity deletePicture(@RequestBody MediaDto mediaDto) {
        return mediaService.deleteMedia(mediaDto);
    }

    @Operation(
            summary = "get picture's id",
            description = "Get picture Id from postgres")
    @GetMapping(value = "/picture/{email}")
    public ResponseEntity getPictureId(@PathVariable("email") String email) {
        return mediaService.getFileId(email);
    }

    @Operation(
            summary = "get picture",
            description = "Get picture itself from minio and postgres")
    @GetMapping(value = "/picture")
    public ResponseEntity getPicture(@RequestParam("fileId") String fileId) {
        return mediaService.getFile(fileId);
    }

}
