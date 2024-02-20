package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.app.MultipartDto;
import org.education.network.dto.request.ContentDto;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.dto.request.UserMediaDto;
import org.education.network.mapping.MultipartFileMapper;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.util.JwtUtil;
import org.education.network.util.ResponseEntityUtil;
import org.education.network.web.exceptions.FileHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        return ResponseEntityUtil.get(HttpStatus.OK, userMediaDto.getFile().getOriginalFilename() + " successfully saved");
    }

    public ResponseEntity deleteMedia(DeleteMediaDto deleteMediaDto, String subject) {
        UserProfile profile = repository.findByEmail(subject);
        fileService.deleteFile(deleteMediaDto, profile.getId().toString());
        return ResponseEntityUtil.get(HttpStatus.OK, deleteMediaDto.getFileName() + " successfully deleted");
    }

    public ResponseEntity getPicture(ContentDto contentDto) {
        String id = repository
                .findByEmail(jwtUtil.getJwtDto(contentDto.getToken()).getUsername())
                .getId()
                .toString();

        return ResponseEntityUtil.get(HttpStatus.OK, fileService.getFile(contentDto.getBucket(),
                contentDto.getFolder(),
                id));
    }
}
