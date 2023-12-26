package org.education.network.mapping;

import org.education.network.dto.request.MediaRequestDto;
import org.education.network.model.Media;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MediaMapper {

    Media toEntity(MediaRequestDto mediaRequestDto);

    MediaRequestDto toDto(Media media);

}
