package org.education.network.dto.request;

import lombok.Builder;
import lombok.Data;
import org.education.network.enumtypes.Bucket;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UserMediaDto {

    private Bucket bucket;
    private MultipartFile file;

}
