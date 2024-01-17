package org.education.network.service.controllerService;


import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.PostDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.mapping.PostMapper;
import org.education.network.model.Post;
import org.education.network.model.repository.PostRepository;
import org.education.network.model.repository.UserProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public ResponseEntity createPost(PostDto postDto) {

        Post post = postMapper.toEntity(postDto);

        postRepository.save(post);

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

    public ResponseEntity getPost(String postId) {
        Post post = postRepository.getPostById(postId);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok().body(CommonResponse.builder()
                .hasErrors(false)
                .body(postDto)
                .createdAt(Instant.now().toString())
                .build());
    }

}
