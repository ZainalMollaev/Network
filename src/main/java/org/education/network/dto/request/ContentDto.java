package org.education.network.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.education.network.enumtypes.Bucket;

@Data
@AllArgsConstructor
public class ContentDto {

    private Bucket bucket;
    private String folder;
    private String token;

}
