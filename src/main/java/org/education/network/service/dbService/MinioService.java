package org.education.network.service.dbService;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.education.network.properties.MinioAppProperties;
import org.education.network.security.exceptions.BadMinioRequestException;
import org.education.network.security.exceptions.FileHandlerException;
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

    public void uploadFile(String photoId, InputStream file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .stream(file,
                                    file.available(),
                                    -1)
                            .contentType(properties.getJpegType())
                            .object(photoId)
                            .build()
            );
        } catch (MinioException e) {
            throw new BadMinioRequestException(e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new FileHandlerException(e);
        }
    }

    public void deleteFile(String photoId) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .object(photoId)
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

    public Iterable<Result<Item>> getAllObjects() {
        return minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(properties.getBucket())
                .build());
    }

    private void createBucket() {
        try {
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucket()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.getBucket()).build());
            }
        } catch (MinioException e) {
            throw new BadMinioRequestException(e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new FileHandlerException(e);
        }
    }

    @PostConstruct
    private void init() {
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
