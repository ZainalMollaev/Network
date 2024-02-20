package org.education.network.mapping;

import org.education.network.dto.request.PostDto;
import org.education.network.model.post.Post;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, imports = UUID.class)
public abstract class PostMapper {

    @Autowired
    private UserProfileRepository repository;

    public abstract List<PostDto> postDtoList(List<Post> post);

    @Mapping( target = "id", expression = "java(UUID.randomUUID())")
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

}
