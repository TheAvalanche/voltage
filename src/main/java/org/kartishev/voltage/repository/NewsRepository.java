package org.kartishev.voltage.repository;

import org.kartishev.voltage.domain.News;
import org.kartishev.voltage.domain.enumeration.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {

    Page<News> findAllByLanguage(Language language, Pageable pageable);

}
