package org.education.network.model.repository;

import org.education.network.model.profile.Hashtags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagsRepository extends JpaRepository<Hashtags, Long> {
}