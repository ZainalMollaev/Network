package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.app.MultipartDto;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.dto.request.PostDto;
import org.education.network.enumtypes.Bucket;
import org.education.network.mapping.MultipartFileMapper;
import org.education.network.mapping.PostMapper;
import org.education.network.model.post.Post;
import org.education.network.model.repository.PostRepository;
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

    public String createPost(PostDto postDto, String subject) {
        postDto.setEmail(subject);
        Post post = postMapper.toEntity(postDto);
        Post savedPost = postRepository.save(post);

        List<MultipartDto> multipartDtos = fileMapper.toDtoList(postDto.getFiles(), Bucket.POSTS);

        fileService.saveFile(Bucket.POSTS.getBucket(), savedPost.getId().toString(), multipartDtos);

        return "Post has created";
    }

    public List<Post> getAllPostsByEmail(String email) {
        return postRepository.getPostsByEmail(email);
    }

    public List<PostDto> getAllPostsDtoByEmail(String email) {
        return postMapper.postDtoList(getAllPostsByEmail(email));
    }

    public PostDto getPostDto(UUID postId) {
        return postMapper.toDto(getPost(postId));
    }

    public Post getPost(UUID postId) {
        return postRepository.getPostById(postId);
    }

    public String deleteFile(DeleteMediaDto deleteMediaDto, String subject) {
        fileService.deleteFile(deleteMediaDto, subject);
        return deleteMediaDto.getFileName() + " successfully deleted";
    }

}
