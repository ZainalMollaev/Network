package org.education.network.service;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.enumtypes.Bucket;
import org.education.network.properties.FileProperties;
import org.education.network.web.exceptions.FileHandlerException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileService {

    //todo ошибка если пытаются добавить файл а пользователя такого нет

    private final MinioService minioService;
    private final FileProperties fileProperties;

    //todo Добавить bucket в сигнатуру
    public byte[] getFile(String photoId) {
        try {
            return minioService.getFile(Bucket.POSTS, photoId).readAllBytes();
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
    }

    public void saveFile(String bucket, String id, List<MultipartFile> multipartFiles) {

        multipartFiles
                .forEach(file -> {
                    fileProperties
                            .getCompresses()
                            .stream()
                            .filter(compress -> {
                                String filename = file.getOriginalFilename().replaceAll("([-]\\d)?[.](jpg|jpeg|png)", "");
                                return compress
                                        .getName()
                                        .contains(filename);
                                    })
                            .forEach(compress -> {

                                try(ByteArrayOutputStream ous = new ByteArrayOutputStream()) {

                                    Thumbnails.of(file.getInputStream())
                                            .size(compress.getHeight(), compress.getWidth())
                                            .outputFormat(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1))
                                            .toOutputStream(ous);

                                    minioService.uploadFile(bucket,
                                            id + "/" + file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")),
                                            new ByteArrayInputStream(ous.toByteArray()));

                                } catch (IOException e) {
                                    throw new FileHandlerException(e);
                                }

                            });
                });

    }

    public void deleteFile(List<DeleteMediaDto> deleteMediaDto, String id) {
        for (DeleteMediaDto file:
                deleteMediaDto) {
            minioService.deleteFile(file.getBucket(), id + "/" + file.getFileName());
        }
    }

}
