package org.kartishev.voltage.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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

/**
 * REST controller for managing BlogCategory.
 */
@RestController
@RequestMapping("/api")
public class BlogCategoryResource {

    private final Logger log = LoggerFactory.getLogger(BlogCategoryResource.class);

    private static final String ENTITY_NAME = "blogCategory";

    private final BlogCategoryService blogCategoryService;

    public BlogCategoryResource(BlogCategoryService blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }

    /**
     * POST  /blog-categories : Create a new blogCategory.
     *
     * @param blogCategoryDTO the blogCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blogCategoryDTO, or with status 400 (Bad Request) if the blogCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blog-categories")
    @Timed
    public ResponseEntity<BlogCategoryDTO> createBlogCategory(@Valid @RequestBody BlogCategoryDTO blogCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save BlogCategory : {}", blogCategoryDTO);
        if (blogCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new blogCategory cannot already have an ID")).body(null);
        }
        BlogCategoryDTO result = blogCategoryService.save(blogCategoryDTO);
        return ResponseEntity.created(new URI("/api/blog-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blog-categories : Updates an existing blogCategory.
     *
     * @param blogCategoryDTO the blogCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blogCategoryDTO,
     * or with status 400 (Bad Request) if the blogCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the blogCategoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blog-categories")
    @Timed
    public ResponseEntity<BlogCategoryDTO> updateBlogCategory(@Valid @RequestBody BlogCategoryDTO blogCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update BlogCategory : {}", blogCategoryDTO);
        if (blogCategoryDTO.getId() == null) {
            return createBlogCategory(blogCategoryDTO);
        }
        BlogCategoryDTO result = blogCategoryService.save(blogCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blogCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blog-categories : get all the blogCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of blogCategories in body
     */
    @GetMapping("/blog-categories")
    @Timed
    public ResponseEntity<List<BlogCategoryDTO>> getAllBlogCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BlogCategories");
        Page<BlogCategoryDTO> page = blogCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blog-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /blog-categories/:id : get the "id" blogCategory.
     *
     * @param id the id of the blogCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blogCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/blog-categories/{id}")
    @Timed
    public ResponseEntity<BlogCategoryDTO> getBlogCategory(@PathVariable Long id) {
        log.debug("REST request to get BlogCategory : {}", id);
        BlogCategoryDTO blogCategoryDTO = blogCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(blogCategoryDTO));
    }

    /**
     * DELETE  /blog-categories/:id : delete the "id" blogCategory.
     *
     * @param id the id of the blogCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blog-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlogCategory(@PathVariable Long id) {
        log.debug("REST request to delete BlogCategory : {}", id);
        blogCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
