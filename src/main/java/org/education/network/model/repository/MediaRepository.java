package org.education.network.model.repository;

import org.education.network.model.Media;
import org.education.network.model.Post;
import org.education.network.model.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MediaRepository extends JpaRepository<Media, UserProfile> {
}
