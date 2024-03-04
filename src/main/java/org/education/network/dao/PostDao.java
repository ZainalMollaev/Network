package org.education.network.dao;

import lombok.RequiredArgsConstructor;
import org.education.network.model.post.Post;
import org.education.network.model.repository.PostRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostDao {

    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPostsByEmail(String email) {
        return postRepository.getPostsByEmail(email);
    }

    public Post getPost(UUID postId) {
        return postRepository.getPostById(postId);
    }

}
