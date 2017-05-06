package org.kartishev.voltage.repository;

import org.kartishev.voltage.domain.BlogCategory;
import org.kartishev.voltage.domain.enumeration.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BlogCategoryRepository extends JpaRepository<BlogCategory,Long> {

    Page<BlogCategory> findAllByLanguage(Language language, Pageable pageable);

    List<BlogCategory> findAllByLanguage(Language language);

}
