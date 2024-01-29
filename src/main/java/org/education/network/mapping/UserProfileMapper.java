package org.education.network.mapping;

import org.education.network.dto.bd.UserProfileDto;
import org.education.network.enumtypes.Gender;
import org.education.network.model.profile.Language;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.LanguageRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserProfileMapper {

    @Autowired
    private LanguageRepository languageRepository;

    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "password", target = "user.password")
    @Mapping(source = "refreshToken", target = "user.refreshToken")
    @Mapping(source = "name", target = "personMain.name")
    @Mapping(source = "lastname", target = "personMain.lastname")
    @Mapping(source = "birthDate", target = "personMain.birthDate")
    @Mapping(target = "personMain.gender", expression = "java(toGender(userProfileDto.getGender()))")
    @Mapping(source = "title", target = "lastjob.title")
    @Mapping(source = "company", target = "lastjob.company")
    @Mapping(source = "specialization", target = "education.specialization")
    @Mapping(source = "university", target = "education.university")
    @Mapping(source = "languages", target = "languages")
    public abstract UserProfile toEntity(UserProfileDto userProfileDto);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(source = "personMain.name", target = "name")
    @Mapping(source = "personMain.lastname", target = "lastname")
    @Mapping(source = "personMain.birthDate", target = "birthDate")
    @Mapping(source = "personMain.gender", target = "gender")
    @Mapping(source = "lastjob.title", target = "title")
    @Mapping(source = "lastjob.company", target = "company")
    @Mapping(source = "education.specialization", target = "specialization")
    @Mapping(source = "education.university", target = "university")
    @Mapping(source = "languages", target = "languages")
    public abstract UserProfileDto toDto(UserProfile userProfile);

    protected String fromLanguage(Language language) {
        return language == null ? null : language.getName();
    }

    protected Language fromStringToLanguage(String language) {
        return languageRepository
                .getByName(language)
                    .orElseGet(() -> Language.builder()
                    .name(language)
                    .build());
    }

    protected Gender toGender(String gender){
        return Gender.valueOf(gender);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "password", target = "user.password")
    @Mapping(source = "refreshToken", target = "user.refreshToken")
    @Mapping(source = "name", target = "personMain.name")
    @Mapping(source = "lastname", target = "personMain.lastname")
    @Mapping(source = "birthDate", target = "personMain.birthDate")
    @Mapping(source = "gender", target = "personMain.gender")
    @Mapping(source = "title", target = "lastjob.title")
    @Mapping(source = "company", target = "lastjob.company")
    @Mapping(source = "specialization", target = "education.specialization")
    @Mapping(source = "university", target = "education.university")
    @Mapping(source = "languages", target = "languages")
    public abstract UserProfile partialUpdate(UserProfileDto userProfileDto, @MappingTarget UserProfile userProfile);

}