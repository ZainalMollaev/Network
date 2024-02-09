package org.education.network.mapping;


import org.education.network.dto.request.MultipartDto;
import org.education.network.web.exceptions.FileHandlerException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MultipartFileMapper {

    @Mapping(target = "inputStream", expression = "java(cloneInputStream(multipartFile.getInputStream()))")
    public abstract MultipartDto toDto(MultipartFile multipartFile) throws IOException;

    public abstract List<MultipartDto> toDtoList(List<MultipartFile> multipartFile);

    //todo нормально обработать exception
    public InputStream cloneInputStream(InputStream inputStream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            inputStream.transferTo(baos);
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

}
