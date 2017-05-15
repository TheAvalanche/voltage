package org.kartishev.voltage.repository;

import org.kartishev.voltage.domain.Slide;
import org.kartishev.voltage.domain.enumeration.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Slide entity.
 */
@SuppressWarnings("unused")
public interface SlideRepository extends JpaRepository<Slide,Long> {

    Page<Slide> findAllByLanguage(Language language, Pageable pageable);

    List<Slide> findAllByLanguage(Language language);

}
