package org.education.network.model.repository;

import org.education.network.model.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    //todo Добавить отложенные запросы

    @Transactional
    @Modifying
    @Query("UPDATE UserProfile up " +
            "SET up.avatar = null " +
            "WHERE up.id = " +
            "(SELECT u.id " +
            "FROM User u " +
            "WHERE u.email = :email)")
    void deleteAvatarByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserProfile up " +
            "SET up.avatar = :avatar " +
            "WHERE up.id = " +
            "(SELECT u.id " +
            "FROM User u " +
            "WHERE u.email = :email) ")
    void updateAvatarByEmail(String email, String avatar);

}