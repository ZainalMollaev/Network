package org.education.network.service;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.education.network.dto.app.MultipartDto;
import org.education.network.dto.request.DeleteMediaDto;
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

    private static String FILE_DELIMETER = "/";

    //todo ошибка если пытаются добавить файл а пользователя такого нет
    private final MinioService minioService;
    private final FileProperties fileProperties;

    //todo Добавить bucket в сигнатуру
    public byte[] getFile(Bucket bucket, String folder, String photoId) {
        try {
            return minioService.getFile(bucket,
                                    photoId + "/" + folder + "/" + bucket.getBucket())
                    .readAllBytes();
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
    }


    //todo сделать exception на превышение размера файла
    @Async
    public void saveFile(String bucket, String id, List<MultipartDto> multipartFiles) {

        List<FileProperties.Compress> compresses = fileProperties.getProperCompress(bucket);

        compresses.forEach(compress ->
                multipartFiles.forEach(file -> {

                    String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf("."));

                    if(!isNotMultiple(fileName, compress)) {

                        try (ByteArrayOutputStream ous = new ByteArrayOutputStream()) {
                            String extension = getFileExtension(file);

                            Thumbnails.of(file.getCloneInputStream())
                                    .size(compress.getHeight(), compress.getWidth())
                                    .outputFormat(extension)
                                    .toOutputStream(ous);

                            String filePath = getFilePath(id, compress, fileName);

                            minioService.uploadFile(bucket,
                                    filePath,
                                    new ByteArrayInputStream(ous.toByteArray()));

                        } catch (IOException e) {
                            throw new FileHandlerException(e);
                        }
                    }

                })
        );
    }

    private boolean isNotMultiple(String fileName, FileProperties.Compress compress){
        return fileName.matches("\\w+[-]\\d+") && !fileName.contains(compress.getName());
    }

    private String getFileExtension(MultipartDto file) {
        return file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1);
    }

    private String getFilePath(String id, FileProperties.Compress compress, String fileName) {
        return id +
                FILE_DELIMETER +
                compress.getName() +
                FILE_DELIMETER +
                fileName;

    }

    public void deleteFile(DeleteMediaDto deleteMediaDto, String id) {

        List<FileProperties.Compress> compresses = fileProperties.getProperCompress(deleteMediaDto.getBucket().getBucket());
        compresses.forEach( compress -> {
                    String path = id + "/" + compress.getName() + "/" + deleteMediaDto.getFileName();
                    minioService.deleteFile(deleteMediaDto.getBucket(),
                            path);
                }
        );

    }

}
