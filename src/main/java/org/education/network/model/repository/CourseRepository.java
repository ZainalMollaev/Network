package org.education.network.model.repository;

import org.education.network.model.resume.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}