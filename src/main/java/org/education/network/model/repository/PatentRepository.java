package org.education.network.model.repository;

import org.education.network.model.resume.Patent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatentRepository extends JpaRepository<Patent, Long> {
}