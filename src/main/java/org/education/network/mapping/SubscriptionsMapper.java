package org.education.network.mapping;

import org.education.network.dto.bd.SubscriptionDto;
import org.education.network.model.profile.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionsMapper {

    @Mapping(target = "name", expression = "java(getName(user))")
    @Mapping(source = "lastjob.company", target = "company")
    @Mapping(source = "lastjob.title", target = "title")
    @Mapping(source = "location", target = "location")
    //@Mapping(target = "imgs", expression = "java()")
    SubscriptionDto toUserSubcriptionDto(UserProfile user);

    private String getName(UserProfile userProfile) {
        return userProfile.getPersonMain().getLastname() + " " + userProfile.getPersonMain().getName();
    }

    private List<byte[]> getImgs() {

        return new ArrayList<>();
    }

}
