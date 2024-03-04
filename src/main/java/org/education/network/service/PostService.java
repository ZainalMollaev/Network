package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dao.PostDao;
import org.education.network.dto.app.MultipartDto;
import org.education.network.dto.request.PostDto;
import org.education.network.enumtypes.Bucket;
import org.education.network.mapping.MultipartFileMapper;
import org.education.network.mapping.PostMapper;
import org.education.network.model.post.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final FileService fileService;
    private final MultipartFileMapper fileMapper;
    private final PostMapper postMapper;

    private final PostDao postDao;

    public String createPost(PostDto postDto, String subject) {
        postDto.setEmail(subject);
        Post post = postDao.createPost(postMapper.toEntity(postDto));

        List<MultipartDto> multipartDtos = fileMapper.toDtoList(postDto.getFiles(), Bucket.POSTS);

        fileService.saveFile(Bucket.POSTS.getBucket(), post.getId().toString(), multipartDtos);

        return "Post has created";
    }

    public List<Post> getAllPostsByEmail(String email) {
        return postDao.getAllPostsByEmail(email);
    }

    public List<PostDto> getAllPostsDtoByEmail(String email) {
        return postMapper.postDtoList(getAllPostsByEmail(email));
    }

    public PostDto getPostDto(UUID postId) {
        return postMapper.toDto(getPost(postId));
    }

    public Post getPost(UUID postId) {
        return postDao.getPost(postId);
    }

}
