package org.education.network.service.dbService;

import lombok.SneakyThrows;
import org.assertj.core.util.IterableUtil;
import org.education.network.properties.MinioAppProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MinioServiceTest {

    @Autowired
    private MinioService minioService;
    @Autowired
    MinioAppProperties properties;

    @SneakyThrows
    @Test
    void goodWorkOfMinio() {
        String photoId = UUID.randomUUID().toString();
        minioService.uploadFile(photoId, new FileInputStream("src/test/resources/test-item/up.jpeg"));
        assertNotNull(minioService.getFile(photoId));
        minioService.deleteFile(photoId);
        assertEquals(0, IterableUtil.sizeOf(minioService.getAllObjects()));
        assertEquals("buck-test", properties.getBucket());
    }



}
