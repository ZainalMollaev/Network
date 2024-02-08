package org.education.network.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.education.network.web.exceptions.FileHandlerException;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MultipartDto {

    private String name;
    private String originalFilename;
    private byte[] bytes;
    private Resource resource;
    private String contentType;
    private InputStream inputStream;
    private long size;

    public InputStream getCloneInputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            this.inputStream.transferTo(baos);
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

}
