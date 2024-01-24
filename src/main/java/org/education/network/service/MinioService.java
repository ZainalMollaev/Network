package org.education.network.service;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.education.network.properties.MinioAppProperties;
import org.education.network.web.exceptions.BadMinioRequestException;
import org.education.network.web.exceptions.FileHandlerException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MinioService {

    private MinioClient minioClient;
    private final MinioAppProperties properties;

    public void uploadFile(String bucket, String filePath, InputStream file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .stream(file,
                                    file.available(),
                                    -1)
                            .contentType(properties.getJpegType())
                            .object(filePath)
                            .build()
            );


        } catch (MinioException e) {
            throw new BadMinioRequestException(e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new FileHandlerException(e);
        }
    }

    public void deleteFile(String bucket, String fileId) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileId)
                            .build()
            );
        } catch (MinioException e) {
            throw new BadMinioRequestException(e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new FileHandlerException(e);
        }
    }

    public InputStream getFile(String photoId) {
        try {
            InputStream img = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .object(photoId)
                            .build()
            );
            return img;
        } catch (MinioException e) {
            throw new BadMinioRequestException(e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new FileHandlerException(e);
        }
    }

    public void createBucket() {
        try {
            boolean foundPerson =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getUserBucket()).build());
            boolean foundPost =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getPostBucket()).build());
            if (!foundPerson) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.getUserBucket()).build());
            }
            if (!foundPost) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.getPostBucket()).build());
            }
        } catch (MinioException e) {
            throw new BadMinioRequestException(e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new FileHandlerException(e);
        }
    }

    @PostConstruct
    public void init() {
        minioClient =
                MinioClient.builder()
                        .endpoint(
                                properties.getHost(),
                                properties.getPort(),
                                properties.isSecure())
                        .credentials(
                                properties.getAccessToken(),
                                properties.getSecretToken())
                        .build();
        createBucket();
    }

}
