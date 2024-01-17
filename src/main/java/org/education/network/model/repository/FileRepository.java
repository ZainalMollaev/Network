package org.education.network.model.repository;

import org.education.network.model.File;
import org.education.network.model.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, UserProfile> {
}
