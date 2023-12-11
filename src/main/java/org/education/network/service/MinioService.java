package org.education.network.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService() {
        minioClient =
                MinioClient.builder()
                        .endpoint("https://localhost", 9000, false)
                        .credentials("TQE5viifimaaSC6iSYhA",
                                "e9c8lPtMLbYgdYkcOGNcQxN3Y1mIdWQCvkwlDfVi")
                        .build();
    }

    public void uploadAvatar(String email, InputStream file)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException{
        try {
            createBucket();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("buck-avatar")
                            .stream(file, file.available(), -1)
                            .contentType("image/jpeg")
                            .object("avatar/" + email)
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAvatar(String email) throws IOException, NoSuchAlgorithmException, InvalidKeyException{
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket("buck-avatar")
                            .object("avatar/" + email)
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }
    }

    public void createBucket()
            throws IOException, NoSuchAlgorithmException, InvalidKeyException{
        try {
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("buck-avatar").build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("buck-avatar").build());
            }
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }


    }

}
