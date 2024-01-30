package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.dto.request.UserMediaDto;
import org.education.network.service.MediaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Tag(name = "MediaController", description = "CRUD of media")
public class MediaController {
    //todo тест и exception handler на неправильный логин
    private final MediaService mediaService;

    @Operation(
            summary = "save avatar or backImg")
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity savePicture(@ModelAttribute UserMediaDto userMediaDto, Principal principal, Authentication auth) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return mediaService.saveMedia(userMediaDto, principal.getName());
    }

    @Operation(
            summary = "delete user profile file")
    @DeleteMapping(value = "/file")
    public ResponseEntity deletePicture(@RequestBody DeleteMediaDto deleteMediaDto, Principal principal) {
        return mediaService.deleteMedia(deleteMediaDto, principal.getName());
    }

    @Operation(
            summary = "get picture",
            description = "Get picture itself")
    @GetMapping(value = "/picture")
    public ResponseEntity getPicture(@RequestParam("fileId") String fileId) {
        return mediaService.getPicture(fileId);
    }

}
