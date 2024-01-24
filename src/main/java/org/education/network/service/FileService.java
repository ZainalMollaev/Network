package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.security.exceptions.FileHandlerException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileService {

    //todo ошибка если пытаются добавить файл а пользователя такого нет

    private final MinioService minioService;

    public byte[] getFile(String photoId) {
        try {
            return minioService.getFile(photoId).readAllBytes();
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
    }

    public void saveFile(String bucket, String id, List<MultipartFile> multipartFiles) {

        for (MultipartFile file:
             multipartFiles) {

            try {
                minioService.uploadFile(bucket, id + "/" + file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")), file.getInputStream());
            } catch (IOException e) {
                throw new FileHandlerException(e);
            }
        }

    }

    public void deleteFile(List<DeleteMediaDto> deleteMediaDto) {
        for (DeleteMediaDto file:
                deleteMediaDto) {
            minioService.deleteFile(file.getBucket(), file.getId() + "/" + file.getFileName());
        }
    }

}
