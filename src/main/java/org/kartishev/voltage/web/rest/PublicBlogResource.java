package org.kartishev.voltage.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.service.BlogService;
import org.kartishev.voltage.service.dto.BlogDTO;
import org.kartishev.voltage.web.rest.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
public class PublicBlogResource {


    private final BlogService blogService;

    public PublicBlogResource(BlogService blogService) {
        this.blogService = blogService;
    }


    @GetMapping("/blogs")
    @Timed
    public ResponseEntity<List<BlogDTO>> getAllBlogs(@ApiParam Pageable pageable) {
        Page<BlogDTO> page = blogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/blogs/lang/{lang}")
    @Timed
    public ResponseEntity<List<BlogDTO>> getAllBlogsByLanguage(@PathVariable String lang, @ApiParam Pageable pageable) {
        Page<BlogDTO> page = blogService.findAllByLanguage(Language.getByShortName(lang), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/blogs/category/{id}")
    @Timed
    public ResponseEntity<List<BlogDTO>> getAllBlogsByCategory(@PathVariable Long id, @ApiParam Pageable pageable) {
        Page<BlogDTO> page = blogService.findAllByBlogCategories(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/blogs/{id}")
    @Timed
    public ResponseEntity<BlogDTO> getBlog(@PathVariable Long id) {
        BlogDTO result = blogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}
