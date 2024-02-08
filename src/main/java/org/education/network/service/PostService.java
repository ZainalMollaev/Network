package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.dto.request.MultipartDto;
import org.education.network.dto.request.PostDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.enumtypes.Bucket;
import org.education.network.mapping.MultipartFileMapper;
import org.education.network.mapping.PostMapper;
import org.education.network.model.Post;
import org.education.network.model.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final FileService fileService;
    private final MultipartFileMapper fileMapper;

    public ResponseEntity createPost(PostDto postDto, String subject) {
        postDto.setEmail(subject);
        Post savedPost = postRepository.save(postMapper.toEntity(postDto));

        List<MultipartDto> multipartDtos = fileMapper.toDtoList(postDto.getFiles());

        fileService.saveFile(Bucket.POSTS.getBucket(), savedPost.getId().toString(), multipartDtos);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity getAllPostsByEmail(String email) {

        List<Post> posts = postRepository.getPostsByEmail(email);
        return ResponseEntity.ok(CommonResponse.builder()
                .hasErrors(false)
                .body(postMapper.postDtoList(posts))
                .createdAt(Instant.now().toString())
            .build());

    }

    public ResponseEntity getPost(UUID postId) {
        Post post = postRepository.getPostById(postId);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok().body(CommonResponse.builder()
                .hasErrors(false)
                .body(postDto)
                .createdAt(Instant.now().toString())
                .build());
    }

    public ResponseEntity deleteFile(DeleteMediaDto deleteMediaDto, String subject) {

        fileService.deleteFile(Collections.singletonList(deleteMediaDto), subject);

        return ResponseEntity.ok(CommonResponse.builder()
                        .hasErrors(false)
                        .body(deleteMediaDto.getFileName() + " successfully deleted")
                .build());
    }
}
