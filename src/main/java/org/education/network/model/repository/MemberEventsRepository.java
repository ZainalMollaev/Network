package org.education.network.model.repository;

import org.education.network.model.profile.MemberEvents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberEventsRepository extends JpaRepository<MemberEvents, Long> {
}