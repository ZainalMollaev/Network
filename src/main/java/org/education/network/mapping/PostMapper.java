package org.education.network.mapping;

import org.education.network.dto.request.PostDto;
import org.education.network.model.Post;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.service.FileService;
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
    private UserProfileRepository repository;
    @Autowired
    private FileService fileService;

    public abstract List<PostDto> postDtoList(List<Post> post);
    public abstract List<Post> postList(List<PostDto> postDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "userProfile", expression = "java(getProfile(postDto.getEmail()))")
    @Mapping(target = "fileList", expression = "java(saveFiles(postDto))")
    public abstract Post toEntity(PostDto postDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "userProfile.user.email", target = "email")
    @Mapping(target = "ids", expression = "java(getIds(post.getFileList()))")
    public abstract PostDto toDto(Post post);

    protected List<File> saveFiles(PostDto postDto) {
        return fileService.saveFilesForPost(postDto);
    }

    protected UserProfile getProfile(String email) {
        UserProfile profile = repository.findByEmail(email);
        return profile;
    }

    protected List<String> getIds(List<File> mediaList) {
        List<String> fileList = new ArrayList<>();
        for (File file : mediaList) {
            fileList.add(file.getFileId().toString());
        }

        return fileList;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Post partialUpdate(PostDto postDto, @MappingTarget Post post);

}