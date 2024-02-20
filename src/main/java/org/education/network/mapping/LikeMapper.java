package org.education.network.mapping;

import org.education.network.dto.bd.LikeDto;
import org.education.network.model.post.Like;
import org.education.network.model.post.Post;
import org.education.network.model.profile.UserProfile;
import org.education.network.service.PostService;
import org.education.network.service.UserProfileService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, imports = UUID.class)
public abstract class LikeMapper {

    @Autowired
    private PostService postService;
    @Autowired
    private UserProfileService profileService;

    @Mapping( target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "post", expression = "java(toPost(likeDto.getPostId()))")
    @Mapping(target = "userProfile", expression = "java(toProfile(likeDto.getUsername()))")
    public abstract Like toEntity(LikeDto likeDto);

    public abstract LikeDto toDto(Like like);

    public Post toPost(String id) {
        return postService.getPost(UUID.fromString(id));
    }

    public UserProfile toProfile(String username) {
        return profileService.getUserProfile(username);
    }
}