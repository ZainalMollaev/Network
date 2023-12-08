package org.education.network.model.repository;

import org.education.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query("SELECT refreshToken " +
            "FROM User " +
            "WHERE email = :email")
    String findRefreshByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User " +
            "set refreshToken = :refreshToken " +
            "WHERE email = :email")
    void updateRefreshByEmail(String email, String refreshToken);

}