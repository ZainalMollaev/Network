package org.education.network.model.repository;

import org.education.network.model.post.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
}