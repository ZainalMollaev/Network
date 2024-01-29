package org.education.network.model.repository;

import org.education.network.model.profile.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

    Optional<Language> getByName(String name);

}
