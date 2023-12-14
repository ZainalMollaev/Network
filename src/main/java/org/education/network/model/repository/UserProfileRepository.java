package org.education.network.model.repository;

import org.education.network.model.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT up " +
            "FROM UserProfile up " +
            "WHERE up.user.email = :email")
    UserProfile findByEmail(String email);

    @Query("SELECT up.id " +
            "FROM UserProfile up " +
            "WHERE up.user.email = :email")
    Long getIdByEmail(String email);

}
