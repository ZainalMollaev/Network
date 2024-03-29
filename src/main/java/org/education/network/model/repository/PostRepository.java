package org.education.network.model.repository;

import org.education.network.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;
public interface PostRepository extends JpaRepository<Post, UUID> {

    Post getPostById(String id);

    @Query("SELECT up.posts " +
            "FROM UserProfile up " +
            "WHERE up.user.email = :email")
    List<Post> getPostsByEmail(String email);
}