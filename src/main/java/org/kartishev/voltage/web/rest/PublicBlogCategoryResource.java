package org.kartishev.voltage.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.service.BlogCategoryService;
import org.kartishev.voltage.service.dto.BlogCategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/api")
public class PublicBlogCategoryResource {

    private final Logger log = LoggerFactory.getLogger(PublicBlogCategoryResource.class);

    private static final String ENTITY_NAME = "blogCategory";

    private final BlogCategoryService blogCategoryService;

    public PublicBlogCategoryResource(BlogCategoryService blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }


    @GetMapping("/blog-categories")
    @Timed
    public ResponseEntity<List<BlogCategoryDTO>> getAllBlogCategories() {
        List<BlogCategoryDTO> result = blogCategoryService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/blog-categories/lang/{lang}")
    @Timed
    public ResponseEntity<List<BlogCategoryDTO>> getAllBlogCategoriesByLanguage(@PathVariable String lang) {
        List<BlogCategoryDTO> result = blogCategoryService.findAllByLanguage(Language.getByShortName(lang));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/blog-categories/{id}")
    @Timed
    public ResponseEntity<BlogCategoryDTO> getBlogCategory(@PathVariable Long id) {
        BlogCategoryDTO blogCategoryDTO = blogCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(blogCategoryDTO));
    }

}
