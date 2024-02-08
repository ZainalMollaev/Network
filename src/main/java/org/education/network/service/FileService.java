package org.education.network.service;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.dto.request.MultipartDto;
import org.education.network.enumtypes.Bucket;
import org.education.network.properties.FileProperties;
import org.education.network.web.exceptions.FileHandlerException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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


    //todo сделать exception на превышение размера файла
    @Async
    public void saveFile(String bucket, String id, List<MultipartDto> multipartFiles) {
        String filename = multipartFiles.get(0).getOriginalFilename().replaceAll("([-]\\d)?[.](jpg|jpeg|png)", "");

        List<FileProperties.Compress> compresses = fileProperties.getProperCompress(filename);

        compresses.forEach(compress ->
                multipartFiles.forEach(file -> {

                    if(!file.getOriginalFilename().contains("post-1")
                            && (!compress.getName().equals("posts")
                            && compress.getName().contains("posts")))
                        return;

                    try(ByteArrayOutputStream ous = new ByteArrayOutputStream()) {

                        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1);
                        Thumbnails.of(file.getCloneInputStream())
                                .size(compress.getHeight(), compress.getWidth())
                                .outputFormat(substring)
                                .toOutputStream(ous);

                        StringBuilder filePath = new StringBuilder()
                                .append(id)
                                .append("/")
                                .append(compress.getName())
                                .append("/")
                                .append(file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")));

                        minioService.uploadFile(bucket,
                                filePath.toString(),
                                new ByteArrayInputStream(ous.toByteArray()));

                    } catch (IOException e) {
                        throw new FileHandlerException(e);
                    }

                })
        );
    }

    public void deleteFile(List<DeleteMediaDto> deleteMediaDto, String id) {
        for (DeleteMediaDto file:
                deleteMediaDto) {
            minioService.deleteFile(file.getBucket(), id + "/" + file.getFileName());
        }
    }

}
