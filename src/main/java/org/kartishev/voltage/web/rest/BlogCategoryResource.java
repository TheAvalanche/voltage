package org.kartishev.voltage.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.service.BlogCategoryService;
import org.kartishev.voltage.service.dto.BlogCategoryDTO;
import org.kartishev.voltage.web.rest.util.HeaderUtil;
import org.kartishev.voltage.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BlogCategoryResource {

    private final Logger log = LoggerFactory.getLogger(BlogCategoryResource.class);

    private static final String ENTITY_NAME = "blogCategory";

    private final BlogCategoryService blogCategoryService;

    public BlogCategoryResource(BlogCategoryService blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }

    @PostMapping("/blog-categories")
    @Timed
    public ResponseEntity<BlogCategoryDTO> createBlogCategory(@Valid @RequestBody BlogCategoryDTO blogCategoryDTO) throws URISyntaxException {
        if (blogCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new blogCategory cannot already have an ID")).body(null);
        }
        BlogCategoryDTO result = blogCategoryService.save(blogCategoryDTO);
        return ResponseEntity.created(new URI("/api/blog-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/blog-categories")
    @Timed
    public ResponseEntity<BlogCategoryDTO> updateBlogCategory(@Valid @RequestBody BlogCategoryDTO blogCategoryDTO) throws URISyntaxException {
        if (blogCategoryDTO.getId() == null) {
            return createBlogCategory(blogCategoryDTO);
        }
        BlogCategoryDTO result = blogCategoryService.save(blogCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blogCategoryDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/blog-categories")
    @Timed
    public ResponseEntity<List<BlogCategoryDTO>> getAllBlogCategories(@ApiParam Pageable pageable) {
        Page<BlogCategoryDTO> page = blogCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blog-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/blog-categories/lang/{lang}")
    @Timed
    public ResponseEntity<List<BlogCategoryDTO>> getAllBlogCategoriesByLanguage(@PathVariable String lang, @ApiParam Pageable pageable) {
        Page<BlogCategoryDTO> page = blogCategoryService.findAllByLanguage(Language.getByShortName(lang), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blog-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/blog-categories/{id}")
    @Timed
    public ResponseEntity<BlogCategoryDTO> getBlogCategory(@PathVariable Long id) {
        BlogCategoryDTO blogCategoryDTO = blogCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(blogCategoryDTO));
    }

    @DeleteMapping("/blog-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlogCategory(@PathVariable Long id) {
        blogCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
