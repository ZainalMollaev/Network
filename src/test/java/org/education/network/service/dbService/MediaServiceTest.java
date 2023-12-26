package org.education.network.service.dbService;

import org.education.network.model.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MediaServiceTest {

    @Mock
    private UserProfileRepository repository;
    @Mock
    private MinioService minioService;
    @Mock
    private UserProfileRepository profileRepository;
    @InjectMocks
    private MediaService mediaService;

    @Test
    void getFile() {

    }

    @Test
    void deleteMedia() {

    }

    @Test
    void getFileId() {
    }
}