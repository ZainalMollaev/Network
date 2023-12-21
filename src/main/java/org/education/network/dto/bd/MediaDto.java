package org.education.network.dto.bd;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MediaDto {
    private String fileId;
    private String fileType;
    private String contentType;

    @Builder
    public MediaDto(String fileId, String fileType, String contentType) {
        this.fileId = fileId;
        this.fileType = fileType;
        this.contentType = contentType;
    }
}
