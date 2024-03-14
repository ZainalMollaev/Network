package org.education.network.mapping;

import org.education.network.dto.bd.UserProfileDto;
import org.education.network.enumtypes.Gender;
import org.education.network.model.Role;
import org.education.network.model.profile.Language;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.LanguageRepository;
import org.education.network.model.repository.RoleRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = PostMapper.class)
public abstract class UserProfileMapper {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "password", target = "user.password")
    @Mapping(source = "refreshToken", target = "user.refreshToken")
    @Mapping(source = "name", target = "personMain.name")
    @Mapping(source = "lastname", target = "personMain.lastname")
    @Mapping(source = "birthDate", target = "personMain.birthDate")
    @Mapping(target = "personMain.gender", expression = "java(toGender(userProfileDto.getGender()))")
    @Mapping(source = "birthPrivacy", target = "personMain.birthPrivacy")
    @Mapping(source = "title", target = "lastjob.title")
    @Mapping(source = "company", target = "lastjob.company")
    @Mapping(source = "jobPrivacy", target = "lastjob.jobPrivacy")
    @Mapping(source = "specialization", target = "education.specialization")
    @Mapping(source = "university", target = "education.university")
    @Mapping(source = "educationPrivacy", target = "education.educationPrivacy")
    @Mapping(source = "languages", target = "languages")
    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "location", target = "location.location")
    @Mapping(source = "locationPrivacy", target = "location.locationPrivacy")
    public abstract UserProfile toEntity(UserProfileDto userProfileDto);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(source = "personMain.name", target = "name")
    @Mapping(source = "personMain.lastname", target = "lastname")
    @Mapping(source = "personMain.birthDate", target = "birthDate")
    @Mapping(source = "personMain.gender", target = "gender")
    @Mapping(source = "personMain.birthPrivacy", target = "birthPrivacy")
    @Mapping(source = "lastjob.title", target = "title")
    @Mapping(source = "lastjob.company", target = "company")
    @Mapping(source = "lastjob.jobPrivacy", target = "jobPrivacy")
    @Mapping(source = "education.specialization", target = "specialization")
    @Mapping(source = "education.university", target = "university")
    @Mapping(source = "education.educationPrivacy", target = "educationPrivacy")
    @Mapping(source = "location.location", target = "location")
    @Mapping(source = "location.locationPrivacy", target = "locationPrivacy")
    @Mapping(source = "languages", target = "languages")
    @Mapping(source = "roles", target = "roles")
    public abstract UserProfileDto toDto(UserProfile userProfile);

    public String fromLanguage(Language language) {
        return language == null ? null : language.getName();
    }

    public Language fromStringToLanguage(String language) {
        Optional<Language> byName = languageRepository.findByName(language);
        return byName.orElseGet(() -> Language.builder().name(language).build());
    }

    public Role fromEnumToModelRole(org.education.network.enumtypes.Role role) {
        Optional<Role> byType = roleRepository.findByType(role);
        return byType.orElseGet(() -> Role.builder().type(role).build());
    }

    public org.education.network.enumtypes.Role fromModelToEnumRole(Role role) {
        return role.getType();
    }

    public abstract List<Role> roleList(List<org.education.network.enumtypes.Role> roles);

    public Gender toGender(String gender){
        return Gender.valueOf(gender.toUpperCase());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "password", target = "user.password")
    @Mapping(source = "refreshToken", target = "user.refreshToken")
    @Mapping(source = "name", target = "personMain.name")
    @Mapping(source = "lastname", target = "personMain.lastname")
    @Mapping(source = "birthDate", target = "personMain.birthDate")
    @Mapping(target = "personMain.gender", expression = "java(toGender(userProfileDto.getGender()))")
    @Mapping(source = "birthPrivacy", target = "personMain.birthPrivacy")
    @Mapping(source = "title", target = "lastjob.title")
    @Mapping(source = "company", target = "lastjob.company")
    @Mapping(source = "jobPrivacy", target = "lastjob.jobPrivacy")
    @Mapping(source = "specialization", target = "education.specialization")
    @Mapping(source = "university", target = "education.university")
    @Mapping(source = "educationPrivacy", target = "education.educationPrivacy")
    @Mapping(source = "languages", target = "languages")
    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "location", target = "location.location")
    @Mapping(source = "locationPrivacy", target = "location.locationPrivacy")
    public abstract UserProfile partialUpdate(UserProfileDto userProfileDto, @MappingTarget UserProfile userProfile);

}