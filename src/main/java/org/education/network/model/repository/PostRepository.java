package org.education.network.model.repository;

import org.education.network.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface PostRepository extends JpaRepository<Post, UUID> {

    Post getPostById(String id);
}