package org.education.network.service.controllerService;


import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.PostDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.mapping.PostMapper;
import org.education.network.model.Media;
import org.education.network.model.Post;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.MediaRepository;
import org.education.network.model.repository.PostRepository;
import org.education.network.model.repository.UserProfileRepository;
import org.education.network.security.exceptions.FileHandlerException;
import org.education.network.service.dbService.MinioService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final MediaRepository mediaRepository;
    private final MinioService minioService;
    private final PostMapper postMapper;

    public ResponseEntity createPost(PostDto postDto) {

        Post post = postMapper.toEntity(postDto);

        for (MultipartFile file :
                postDto.getFiles()) {
            Media post1 = Media.builder()
                    .post(post)
                    .fileType("post")
                    .build();
            try {
                minioService.uploadFile(post1.getFileId().toString(), file.getInputStream());
                mediaRepository.save(post1);
            } catch (IOException e) {
                throw new FileHandlerException(e);
            }
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity getAllPostsByEmail(String email) {
        UserProfile profile = profileRepository.findByEmail(email);
        List<Post> posts = profile.getPosts();

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
