package org.education.network.model.repository;

import org.education.network.model.resume.Recomenadation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecomenadationRepository extends JpaRepository<Recomenadation, Long> {
}