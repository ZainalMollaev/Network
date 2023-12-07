package org.education.network.model.repository;

import org.education.network.model.resume.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}