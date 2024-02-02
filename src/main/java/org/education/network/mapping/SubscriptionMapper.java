package org.education.network.mapping;

import org.education.network.dto.response.SubscriptionDto;
import org.education.network.enumtypes.Bucket;
import org.education.network.model.Post;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.PostRepository;
import org.education.network.service.MinioService;
import org.education.network.web.exceptions.FileHandlerException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class SubscriptionMapper {

    @Autowired
    private MinioService minioService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostRepository postRepository;

    @Mapping(source = "id", target = "id")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(target = "img", expression = "java(getNthImg(post.getId, 1))")
    public abstract SubscriptionDto.SubPostDto toSubPostDto(Post post);

    @Mapping(source = "personMain.name", target = "firstname")
    @Mapping(source = "personMain.lastname", target = "lastname")
    @Mapping(source = "lastjob.title", target = "title")
    @Mapping(source = "lastjob.company", target = "company")
    @Mapping(source = "location", target = "location")
    @Mapping(target = "posts", expression = "java(subPostDtos(profile.getUser().getEmail()))")
    @Mapping(target = "commonSubs")
    public abstract SubscriptionDto toSubDto(UserProfile profile);

    public List<SubscriptionDto.SubPostDto> subPostDtos(String email) {
        List<Post> postsByEmailFetchFirst = postRepository.getPostsByEmailFetchFirst(email);
        List<SubscriptionDto.SubPostDto> subPostDtos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Post post = postsByEmailFetchFirst.get(i);
            subPostDtos.add(toSubPostDto(post));
        }
        return subPostDtos;
    }

    public List<SubscriptionDto.CommonSubs> commonSubs(String email) {
        List<Post> postsByEmailFetchFirst = postRepository.getPostsByEmailFetchFirst(email);
        List<SubscriptionDto.SubPostDto> subPostDtos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Post post = postsByEmailFetchFirst.get(i);
            subPostDtos.add(toSubPostDto(post));
        }
        return subPostDtos;
    }

    public byte[] getNthImg(String id, int i) {
        try {
            return minioService.getFile(Bucket.POSTS, id + "/" +i).readAllBytes();
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
    }


}
