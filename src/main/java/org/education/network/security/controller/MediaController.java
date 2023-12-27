package org.education.network.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.FileDto;
import org.education.network.service.dbService.FileService;
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

    private final FileService fileService;

    @Operation(
            summary = "save picture",
            description = "Save picture")
    @PostMapping(value = "/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity savePicture(@ModelAttribute FileDto fileDto) {
        return fileService.saveAvatarOrBack(fileDto);
    }

    @Operation(
            summary = "delete picture",
            description = "delete picture")
    @DeleteMapping(value = "/picture")
    public ResponseEntity deletePicture(@RequestBody FileDto fileDto) {
        return fileService.deleteFile(fileDto);
    }

    @Operation(
            summary = "get picture's id",
            description = "Get picture's id")
    @GetMapping(value = "/picture/{email}/{type}")
    public ResponseEntity getPictureId(@PathVariable("email") String email,
                                       @PathVariable("type") String type) {
        return fileService.getFileId(email, type);
    }

    @Operation(
            summary = "get picture",
            description = "Get picture itself")
    @GetMapping(value = "/picture")
    public ResponseEntity getPicture(@RequestParam("fileId") String fileId) {
        return fileService.getFile(fileId);
    }

}
