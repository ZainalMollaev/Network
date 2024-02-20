package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.app.MultipartDto;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.dto.request.PostDto;
import org.education.network.enumtypes.Bucket;
import org.education.network.mapping.MultipartFileMapper;
import org.education.network.mapping.PostMapper;
import org.education.network.model.Post;
import org.education.network.model.repository.PostRepository;
import org.education.network.util.ResponseEntityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        Post post = postMapper.toEntity(postDto);
        Post savedPost = postRepository.save(post);

        List<MultipartDto> multipartDtos = fileMapper.toDtoList(postDto.getFiles(), Bucket.POSTS);

        fileService.saveFile(Bucket.POSTS.getBucket(), savedPost.getId().toString(), multipartDtos);

        return ResponseEntityUtil.get(HttpStatus.OK, "Post has created");
    }

    public ResponseEntity getAllPostsByEmail(String email) {
        List<Post> posts = postRepository.getPostsByEmail(email);
        return ResponseEntityUtil.get(HttpStatus.OK, postMapper.postDtoList(posts));
    }

    public ResponseEntity getPost(UUID postId) {
        Post post = postRepository.getPostById(postId);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntityUtil.get(HttpStatus.OK, postDto);
    }

    public ResponseEntity deleteFile(DeleteMediaDto deleteMediaDto, String subject) {
        fileService.deleteFile(deleteMediaDto, subject);
        return ResponseEntityUtil.get(HttpStatus.OK, "successfully deleted");
    }
}
