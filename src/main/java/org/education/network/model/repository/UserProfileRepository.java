package org.education.network.model.repository;

import org.education.network.model.profile.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT up " +
            "FROM UserProfile up " +
            "WHERE up.user.email = :email")
    UserProfile findByEmail(String email);

    @Query("SELECT up.subscribes " +
            "FROM UserProfile up " +
            "WHERE up.user.email = :email ")
    Page<UserProfile> findByEmailWithPage(String email, Pageable pageable);

    //@EntityGraph(attributePaths = "userProfilePosts", type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "SELECT up.subscribes " +
            "FROM UserProfile up " +
            "WHERE up.user.email = :email")
    List<UserProfile> getSubscribersByEmail(String email);

    @Query(value = "SELECT u.email\n" +
                    "FROM tbl_subscribers tbl\n" +
                    "JOIN users u on u.id = tbl.subscriber_id\n" +
                    "CROSS JOIN tbl_subscribers subs\n" +
                    "WHERE tbl.subscriber_id = subs.subscriber_id\n" +
                    "    and tbl.profile_id = :id\n" +
                    "    and tbl.profile_id != subs.profile_id", nativeQuery = true)
    List<String> getCommonSubs(UUID id);

}
