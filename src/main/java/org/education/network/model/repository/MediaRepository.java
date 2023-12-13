package org.education.network.model.repository;

import org.education.network.model.Media;
import org.education.network.model.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MediaRepository extends JpaRepository<Media, UserProfile> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Media m " +
            "WHERE m.id = :id ")
    void deleteAllById(String id);

}
