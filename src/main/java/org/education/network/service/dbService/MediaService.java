package org.education.network.service.dbService;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.MediaRequestDto;
import org.education.network.model.Media;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.MediaRepository;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.security.exceptions.FileHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MinioService minioService;
    private final UserProfileRepository profileRepository;
    private final MediaRepository mediaRepository;

    public ResponseEntity getFile(String photoId) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(minioService.getFile(photoId).readAllBytes());
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
    }

    public ResponseEntity saveMedia(MediaRequestDto mediaRequestDto) {

        UserProfile userProfile = profileRepository.findByEmail(mediaRequestDto.getEmail());
        String id = UUID.randomUUID().toString();
        Media media = Media.builder()
                .id(id)
                .fileType(mediaRequestDto.getFileType())
                .build();
        userProfile.setMedia(media);
        mediaRequestDto.setFileId(media.getId());
        profileRepository.saveAndFlush(userProfile);
        try {
            minioService.uploadFile(id, mediaRequestDto.getFile().getInputStream());
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteMedia(MediaRequestDto mediaRequestDto) {
            UserProfile userProfile = profileRepository.findByEmail(mediaRequestDto.getEmail());
            String id = userProfile.getMedia().getId();
            minioService.deleteFile(id);
            userProfile.setMedia(null);
            profileRepository.flush();
            mediaRepository.deleteAllById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity getFileId(String email) {
        UserProfile userProfile = profileRepository.findByEmail(email);
        String fileId = userProfile.getMedia().getId();

        return ResponseEntity.ok(MediaRequestDto.builder()
                        .fileId(fileId)
                .build());
    }

}
