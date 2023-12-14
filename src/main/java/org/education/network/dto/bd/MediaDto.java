package org.education.network.dto.bd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MediaDto {
    private String fileId;
    private String fileType;

    @Builder
    public MediaDto(String fileId, String fileType) {
        this.fileId = fileId;
        this.fileType = fileType;
    }
}
