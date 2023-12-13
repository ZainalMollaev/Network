package org.education.network.service.dbService;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.MediaDto;
import org.education.network.model.Media;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.MediaRepository;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MinioService minioService;
    private final UserProfileRepository profileRepository;
    private final MediaRepository mediaRepository;

    public ResponseEntity getFile(String photoId)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(minioService.getFile(photoId).readAllBytes());
    }

    public ResponseEntity saveMedia(MediaDto mediaDto)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        UserProfile userProfile = profileRepository.findByEmail(mediaDto.getEmail());
        String id = UUID.randomUUID().toString();
        Media media = Media.builder()
                .id(id)
                .fileType(mediaDto.getFileType())
                .build();
        userProfile.setMedia(media);
        mediaDto.setFileId(media.getId());
        profileRepository.saveAndFlush(userProfile);
        minioService.uploadFile(id, mediaDto.getFile().getInputStream());
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteMedia(MediaDto mediaDto)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        UserProfile userProfile = profileRepository.findByEmail(mediaDto.getEmail());
        String id = userProfile.getMedia().getId();
        minioService.deleteFile(id);
        userProfile.setMedia(null);
        profileRepository.flush();
        mediaRepository.deleteAllById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    public ResponseEntity getFileId(String email) {
        UserProfile userProfile = profileRepository.findByEmail(email);
        String id = userProfile.getMedia().getId();

        return ResponseEntity.ok(MediaDto.builder()
                        .fileId(id)
                        .build());
    }
}
