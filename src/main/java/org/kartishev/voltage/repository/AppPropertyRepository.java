package org.kartishev.voltage.repository;

import org.kartishev.voltage.domain.AppProperty;
import org.kartishev.voltage.domain.enumeration.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the AppProperty entity.
 */
@SuppressWarnings("unused")
public interface AppPropertyRepository extends JpaRepository<AppProperty,Long> {

    Page<AppProperty> findAllByLanguage(Language language, Pageable pageable);

    List<AppProperty> findAllByLanguage(Language language);

}
