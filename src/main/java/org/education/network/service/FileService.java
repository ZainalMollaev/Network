package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.FileDto;
import org.education.network.dto.request.PostDto;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.security.exceptions.FileHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileService {

    //todo ошибка если пытаются добавить файл а пользователя такого нет

    private final MinioService minioService;
    private final UserProfileRepository profileRepository;

    public byte[] getFile(String photoId) {
        try {
            return minioService.getFile(photoId).readAllBytes();
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
    }

    public List<byte[]> getFiveFilesOfUser() {

        return new ArrayList<>();
    }



    public List<File> saveFilesForPost(PostDto postDto) {
        List<File> files = new ArrayList<>();

        for (MultipartFile file :
                postDto.getFiles()) {
            File postFile = File.builder()
                    .fileType("post")
                    .build();
            files.add(postFile);

            try {
                minioService.uploadFile(postFile.getFileId(), file.getInputStream());
            } catch (IOException e) {
                throw new FileHandlerException(e);
            }
        }

        return files;
    }

    public ResponseEntity saveAvatarOrBack(FileDto mediaRequestDto) {

        UserProfile userProfile = profileRepository.findByEmail(mediaRequestDto.getEmail());
        File media = File.builder()
                .fileType(mediaRequestDto.getFileType())
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

    public ResponseEntity deleteFile(FileDto mediaRequestDto) {
        UserProfile userProfile = profileRepository.findByEmail(mediaRequestDto.getEmail());

        File file = userProfile.getFiles()
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
        String id = userProfile.getFiles()
                .stream()
                .filter(
                        i -> i.getFileType().equals(type))
                .findFirst()
                .get()
                .getFileId()
                .toString();

        return ResponseEntity.ok(FileDto.builder()
                .fileId(id)
                .build());
    }

}
