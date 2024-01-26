package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.dto.request.UserMediaDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.enumtypes.Bucket;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class MediaService {

    private final UserProfileRepository repository;
    private final FileService fileService;

    public ResponseEntity saveMedia(UserMediaDto userMediaDto) {
        UserProfile profile = repository.findByEmail(userMediaDto.getUsername());
        fileService.saveFile(Bucket.users.getBucket(), profile.getId(), Collections.singletonList(userMediaDto.getFile()));
        return ResponseEntity.ok(CommonResponse.builder()
                        .hasErrors(false)
                        .body(userMediaDto.getFile().getOriginalFilename() + " successfully saved")
                        .createdAt(Instant.now().toString())
                .build());
    }

    public ResponseEntity deleteMedia(DeleteMediaDto deleteMediaDto) {
        UserProfile profile = repository.findByEmail(deleteMediaDto.getId());
        deleteMediaDto.setId(profile.getId());
        fileService.deleteFile(Collections.singletonList(deleteMediaDto));
        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body(deleteMediaDto.getFileName() + " successfully deleted")
                .createdAt(Instant.now().toString())
                .build());
    }

    public ResponseEntity getPicture(String fileId) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileService.getFile(fileId));
    }
}
