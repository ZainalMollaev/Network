package org.education.network.model.repository;

import org.education.network.model.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
    void updateAvatarByEmail(String email, UUID avatar);

    @Transactional
    @Modifying
    @Query("UPDATE UserProfile up " +
            "SET up.backPhoto = null " +
            "WHERE up.id = " +
            "(SELECT u.id " +
            "FROM User u " +
            "WHERE u.email = :email)")
    void deleteBackPhotoByEmail(String email);

    @Query("SELECT up.avatar " +
            "FROM UserProfile up " +
            "WHERE up.id = " +
            "(SELECT u.id " +
            "FROM User u " +
            "WHERE u.email = :email) ")
    String findAvatarIdByEmail(String email);

    @Query("SELECT up.backPhoto " +
            "FROM UserProfile up " +
            "WHERE up.id = " +
            "(SELECT u.id " +
            "FROM User u " +
            "WHERE u.email = :email) ")
    String findBackPhotoIdByEmail(String email);
}