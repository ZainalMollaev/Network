package org.education.network.mapping;

import org.education.network.dto.request.PostDto;
import org.education.network.dto.response.SubscriptionDto;
import org.education.network.enumtypes.Bucket;
import org.education.network.model.Post;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.service.MinioService;
import org.education.network.web.exceptions.FileHandlerException;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PostMapper {

    @Autowired
    private UserProfileRepository repository;
    @Autowired
    private MinioService minioService;

    public abstract List<PostDto> postDtoList(List<Post> post);
    public abstract List<Post> postList(List<PostDto> postDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "userProfile", expression = "java(getProfile(postDto.getEmail()))")
    public abstract Post toEntity(PostDto postDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "userProfile.user.email", target = "email")
    public abstract PostDto toDto(Post post);

    protected UserProfile getProfile(String email) {
        UserProfile profile = repository.findByEmail(email);
        return profile;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Post partialUpdate(PostDto postDto, @MappingTarget Post post);

}