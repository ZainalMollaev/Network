package org.education.network.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.education.network.dto.bd.MediaDto;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MediaRequestDto extends MediaDto {

    private MultipartFile file;
    private String email;
}
