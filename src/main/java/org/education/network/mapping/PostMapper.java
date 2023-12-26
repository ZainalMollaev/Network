package org.education.network.mapping;

import org.education.network.dto.request.PostDto;
import org.education.network.model.Media;
import org.education.network.model.Post;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.MediaRepository;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.service.dbService.MinioService;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PostMapper {

    @Autowired
    protected MinioService minioService;
    @Autowired
    protected UserProfileRepository repository;
    @Autowired
    protected MediaRepository mediaRepository;

    public abstract List<PostDto> postDtoList(List<Post> post);
    public abstract List<Post> postList(List<PostDto> postDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "userProfile", expression = "java(getProfile(postDto.getEmail()))")
    public abstract Post toEntity(PostDto postDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "userProfile.user.email", target = "email")
    @Mapping(target = "ids", expression = "java(getIds(post.getMediaList()))")
    public abstract PostDto toDto(Post post);

    protected UserProfile getProfile(String email) {
        UserProfile profile = repository.findByEmail(email);
        return profile;
    }

    protected List<String> getIds(List<Media> mediaList) {
        List<String> fileList = new ArrayList<>();
        for (Media media: mediaList) {
            fileList.add(media.getFileId().toString());
        }

        return fileList;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Post partialUpdate(PostDto postDto, @MappingTarget Post post);

}