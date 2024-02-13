package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.app.MultipartDto;
import org.education.network.dto.request.ContentDto;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.dto.request.UserMediaDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.mapping.MultipartFileMapper;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.security.auth.JwtUtil;
import org.education.network.web.exceptions.FileHandlerException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class MediaService {

    private final UserProfileRepository repository;
    private final FileService fileService;
    private final MultipartFileMapper fileMapper;
    private final JwtUtil jwtUtil;

    public ResponseEntity saveMedia(UserMediaDto userMediaDto, String subject) {
        UserProfile profile = repository.findByEmail(subject);
        MultipartDto multipartDto;
        try {
            multipartDto = fileMapper.toDto(userMediaDto.getFile(), userMediaDto.getBucket());
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }

        fileService.saveFile(userMediaDto.getBucket().getBucket(),
                profile.getId().toString(),
                Collections.singletonList(multipartDto));
        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body(userMediaDto.getFile().getOriginalFilename() + " successfully saved")
                .createdAt(Instant.now().toString())
                .build());
    }

    public ResponseEntity deleteMedia(DeleteMediaDto deleteMediaDto, String subject) {
        UserProfile profile = repository.findByEmail(subject);
        fileService.deleteFile(Collections.singletonList(deleteMediaDto), profile.getId().toString());
        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body(deleteMediaDto.getFileName() + " successfully deleted")
                .createdAt(Instant.now().toString())
                .build());
    }

    public ResponseEntity getPicture(ContentDto contentDto) {
        String id = repository
                .findByEmail(jwtUtil.getJwtDto(contentDto.getToken()).getUsername())
                .getId()
                .toString();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileService.getFile(contentDto.getBucket(),
                        contentDto.getFolder(),
                        id
                        ));
    }
}
