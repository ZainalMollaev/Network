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

    @Query(value = "SELECT u.email\n" +
                    "FROM tbl_subscribers tbl\n" +
                    "JOIN users u on u.id = tbl.subscriber_id\n" +
                    "CROSS JOIN tbl_subscribers subs\n" +
                    "WHERE tbl.subscriber_id = subs.subscriber_id\n" +
                    "    and tbl.profile_id = :id\n" +
                    "    and tbl.profile_id != subs.profile_id", nativeQuery = true)
    List<String> getCommonSubs(UUID id);

    @Query(value = "with id_by_email as (\n" +
                    "    SELECT u.id\n" +
                    "    FROM users u\n" +
                    "    WHERE u.email = :email\n" +
                    ")\n" +
                    "SELECT\n" +
                    "    distinct  lastname, name, email\n" +
                    "FROM id_by_email ibe\n" +
                    "JOIN tbl_subscribers tbl\n" +
                    "    ON tbl.profile_id = ibe.id or tbl.subscriber_id = ibe.id\n" +
                    "JOIN user_profile up\n" +
                    "    ON (tbl.profile_id = ibe.id AND tbl.subscriber_id = up.user_id) OR (tbl.subscriber_id = ibe.id AND tbl.profile_id = up.user_id)\n" +
                    "JOIN users u ON up.user_id = u.id\n" +
                    "WHERE concat(up.name, ' ', up.lastname) like :likePattern OR concat(up.lastname, ' ', up.name) like :likePattern \n",
            nativeQuery = true)
    List<NameOnly> findProperSubscriptionsOrSubscribersByName(String email, String likePattern);

    public static interface NameOnly {

        String getName();

        String getLastname();

        String getEmail();

    }
}

















