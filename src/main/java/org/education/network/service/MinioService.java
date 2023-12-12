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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {

    private MinioClient minioClient;
    private final MinioAppProperties properties;

    public void uploadFile(UUID photoId, InputStream file)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException{
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .stream(file,
                                    file.available(),
                                    -1)
                            .contentType(properties.getJpegType())
                            .object(photoId.toString())
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String photoId)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException{
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .object(photoId)
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getFile(String photoId)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException{
        try {
            InputStream img = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .object(photoId)
                            .build()
            );
            return img;
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }
    }

    public void createBucket()
            throws IOException, NoSuchAlgorithmException, InvalidKeyException{
        try {
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucket()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.getBucket()).build());
            }
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init()
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
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
