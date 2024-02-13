package org.education.network.dto.request;

import lombok.Builder;
import lombok.Data;
import org.education.network.enumtypes.Bucket;

@Data
@Builder
public class DeleteMediaDto {

    private String fileName;
    private Bucket bucket;
}
