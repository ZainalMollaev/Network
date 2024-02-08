package org.education.network.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {

    private UUID id;
    private String title;
    private String description;
    private String email;
    private String location;
    private LocalDate creationDate;
    private List<MultipartFile> files;

    
}
