package org.education.network.service.dbService;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.MediaRequestDto;
import org.education.network.model.Media;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.security.exceptions.FileHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MinioService minioService;
    private final UserProfileRepository profileRepository;

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
        Media media = Media.builder()
                .fileType(mediaRequestDto.getFileType())
                .contentType(mediaRequestDto.getFile().getContentType())
                .build();
        userProfile.addMedia(media);
        profileRepository.saveAndFlush(userProfile);
        try {
            minioService.uploadFile(media.getFileId().toString(),
                    mediaRequestDto.getFile().getInputStream());
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteMedia(MediaRequestDto mediaRequestDto) {
        UserProfile userProfile = profileRepository.findByEmail(mediaRequestDto.getEmail());

        Media file = userProfile.getMedia()
                .stream()
                .filter(
                        i -> i.getFileId().toString().equals(mediaRequestDto.getFileId()))
                .findFirst()
                .get();

        minioService.deleteFile(file.getFileId().toString());
        userProfile.deleteMedia(file);
        profileRepository.flush();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity getFileId(String email, String type) {
        UserProfile userProfile = profileRepository.findByEmail(email);
        String id = userProfile.getMedia()
                .stream()
                .filter(
                        i -> i.getFileType().equals(type))
                .findFirst()
                .get()
                .getFileId()
                .toString();

        return ResponseEntity.ok(MediaRequestDto.builder()
                        .fileId(id)
                .build());
    }

}
