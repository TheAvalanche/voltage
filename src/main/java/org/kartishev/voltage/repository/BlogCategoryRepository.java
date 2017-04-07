package org.kartishev.voltage.repository;

import org.kartishev.voltage.domain.BlogCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BlogCategory entity.
 */
@SuppressWarnings("unused")
public interface BlogCategoryRepository extends JpaRepository<BlogCategory,Long> {

}
