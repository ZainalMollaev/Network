package org.education.network.mapping;

import org.education.network.dto.bd.UserProfileDto;
import org.education.network.enumtypes.Gender;
import org.education.network.model.Role;
import org.education.network.model.profile.Language;
import org.education.network.model.profile.UserProfile;
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
    @Mapping(source = "roles", target = "roles")
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
    @Mapping(source = "roles", target = "roles")
    public abstract UserProfileDto toDto(UserProfile userProfile);

    public String fromLanguage(Language language) {
        return language == null ? null : language.getName();
    }

    public Language fromStringToLanguage(String language) {
        return Language.builder()
                .name(language)
                .build();
    }

    public abstract List<Role> roleList(List<org.education.network.enumtypes.Role> roles);

    public Role fromEnumToModelRole(org.education.network.enumtypes.Role role) {
        Optional<Role> byType = roleRepository.findByType(role);

        return byType.orElseGet(() -> Role.builder().type(role).build());

    }

    public org.education.network.enumtypes.Role fromModelToEnumRole(Role role) {
        return role.getType();
    }

    public Gender toGender(String gender){
        return Gender.valueOf(gender.toUpperCase());
    }

//    private List<SubscriptionDto.CommonSubs> getCommonSubs() {
//
//
//
//        return List.of();
//    }

//    @Mapping(source = "personMain.name", target = "firstname")
//    @Mapping(source = "personMain.lastname", target = "lastname")
//    @Mapping(source = "lastjob.title", target = "title")
//    @Mapping(source = "lastjob.company", target = "company")
//    @Mapping(source = "location", target = "location")
//    SubscriptionDto toSubsDto(UserProfile profile);



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