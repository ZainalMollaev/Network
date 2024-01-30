package org.education.network.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UserMediaDto {

    private String bucket;
    private MultipartFile file;

}
