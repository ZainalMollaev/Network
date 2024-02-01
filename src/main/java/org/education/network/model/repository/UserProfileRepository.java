package org.education.network.model.repository;

import jakarta.persistence.NamedEntityGraphs;
import org.education.network.model.profile.UserProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT up " +
            "FROM UserProfile up " +
            "WHERE up.user.email = :email")
    UserProfile findByEmail(String email);

    @Query("SELECT up.id " +
            "FROM UserProfile up " +
            "WHERE up.user.email = :email")
    Long getIdByEmail(String email);

    @EntityGraph(attributePaths = "userProfilePosts", type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "SELECT up.subscribes " +
            "FROM UserProfile up " +
            "WHERE up.user.email = :email")
    List<UserProfile> getSubscriptionsByEmail(String email);

    @Query(value = "SELECT * " +
                    "FROM tbl_subscribers users " +
                    "CROSS JOIN tbl_subscribers subs " +
                    "WHERE users.subscriber_id = subs.user_id " +
                    "AND subs.subscriber_id in (SELECT * " +
                    "FROM users) ", nativeQuery = true)
    List<UserProfile> getCommonSubs();

}
