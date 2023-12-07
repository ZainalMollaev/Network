package org.education.network.model.repository;

import org.education.network.model.profile.EventTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTimeRepository extends JpaRepository<EventTime, Long> {
}