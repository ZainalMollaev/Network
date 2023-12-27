package org.education.network.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class MediaRequestDto {

    private String fileId;
    private String fileType;
    private String contentType;
    private MultipartFile file;
    private String email;
    private PostDto postDto;

}
