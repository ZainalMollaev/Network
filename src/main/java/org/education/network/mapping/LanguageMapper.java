package org.education.network.mapping;

import org.education.network.dto.bd.LanguageDto;
import org.education.network.model.profile.Language;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface LanguageMapper {
    Language toEntity(LanguageDto languageDto);

    LanguageDto toDto(Language language);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Language partialUpdate(LanguageDto languageDto, @MappingTarget Language language);
}