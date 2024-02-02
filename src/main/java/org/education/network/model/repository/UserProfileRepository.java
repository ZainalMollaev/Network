package org.education.network.model.repository;

import org.education.network.model.profile.UserProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

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

    @Query(value = "SELECT tbl.subscriber_id " +
            "FROM tbl_subscribers tbl " +
            "JOIN user_profile up on up.user_id = tbl.profile_id " +
            "CROSS JOIN tbl_subscribers subs " +
            "WHERE users.subscriber_id = subs.subscriber_id " +
            "    and tbl.profile_id = '8ac26cea-b0ed-4855-83e5-8c62a6e59ab7' " +
            "    and tbl.profile_id != subs.profile_id", nativeQuery = true)
    List<UUID> getCommonSubs();

}
