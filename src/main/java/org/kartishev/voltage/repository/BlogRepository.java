package org.kartishev.voltage.repository;

import org.kartishev.voltage.domain.Blog;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Blog entity.
 */
@SuppressWarnings("unused")
public interface BlogRepository extends JpaRepository<Blog,Long> {

    @Query("select distinct blog from Blog blog left join fetch blog.blogCategories")
    List<Blog> findAllWithEagerRelationships();

    @Query("select blog from Blog blog left join fetch blog.blogCategories where blog.id =:id")
    Blog findOneWithEagerRelationships(@Param("id") Long id);

}
