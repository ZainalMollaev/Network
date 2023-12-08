package org.education.network.model.repository;

import org.education.network.model.resume.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}