package org.education.network.db.repository;

import org.education.network.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);
    Boolean existsByEmail(String email);
}