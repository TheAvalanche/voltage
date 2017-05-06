package org.kartishev.voltage.repository;

import org.kartishev.voltage.domain.Blog;
import org.kartishev.voltage.domain.BlogCategory;
import org.kartishev.voltage.domain.News;
import org.kartishev.voltage.domain.enumeration.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog,Long> {

    @Query("select distinct blog from Blog blog left join fetch blog.blogCategories")
    List<Blog> findAllWithEagerRelationships();

    @Query("select blog from Blog blog left join fetch blog.blogCategories where blog.id =:id")
    Blog findOneWithEagerRelationships(@Param("id") Long id);

    Page<Blog> findAllByLanguage(Language language, Pageable pageable);

    Page<Blog> findAllByBlogCategories(BlogCategory blogCategory, Pageable pageable);

}
