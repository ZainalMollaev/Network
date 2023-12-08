package org.education.network.mapping;

import org.education.network.dto.UserProfileDto;
import org.education.network.model.profile.UserProfile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {

    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "password", target = "user.password")
    @Mapping(source = "refreshToken", target = "user.refreshToken")
    @Mapping(source = "name", target = "personMain.name")
    @Mapping(source = "lastname", target = "personMain.lastname")
    @Mapping(source = "birthDate", target = "personMain.birthDate")
    @Mapping(source = "gender", target = "personMain.gender")
    @Mapping(source = "title", target = "lastjob.title")
    @Mapping(source = "company", target = "lastjob.company")
    @Mapping(source = "title", target = "education.specialization")
    @Mapping(source = "company", target = "education.university")
    UserProfile toEntity(UserProfileDto userProfileDto);

    UserProfileDto toDto(UserProfile userProfile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfile partialUpdate(UserProfileDto userProfileDto, @MappingTarget UserProfile userProfile);
}