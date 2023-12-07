package org.education.network.model.repository;

import org.education.network.model.profile.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Events, Long> {
}