package org.education.network.mapping;


import lombok.SneakyThrows;
import org.education.network.dto.app.MultipartDto;
import org.education.network.enumtypes.Bucket;
import org.education.network.web.exceptions.FileHandlerException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
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
    @Mapping(target = "bucket", expression = "java(bucket)")
    public abstract MultipartDto toDto(MultipartFile multipartFile, Bucket bucket) throws IOException;

    public abstract List<MultipartDto> toDtoList(List<MultipartFile> multipartFile, @Context Bucket bucket);

    @SneakyThrows
    public MultipartDto mapConcrete (MultipartFile multipartFile, @Context Bucket bucket){
        return toDto(multipartFile, bucket);
    }

    //todo нормально обработать exception
    public InputStream cloneInputStream(InputStream inputStream) {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            inputStream.transferTo(baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
    }

    @AfterMapping
    public void numberNames(List<MultipartFile> multipartFiles, @MappingTarget List<MultipartDto> multipartDtos) {
        for (int i = 0; i < multipartFiles.size(); i++) {
            if(i != 0) {
                multipartDtos.get(i).setName(multipartDtos.get(i).getBucket().getBucket() + "-" + (i+1));
            } else {
                multipartDtos.get(0).setName(multipartDtos.get(i).getBucket().toString());
            }
        }
    }
}
