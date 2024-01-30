package org.education.network.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteMediaDto {

    private String fileName;
    private String bucket;
}
