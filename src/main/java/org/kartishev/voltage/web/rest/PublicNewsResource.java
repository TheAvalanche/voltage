package org.kartishev.voltage.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.kartishev.voltage.service.NewsService;
import org.kartishev.voltage.service.dto.NewsDTO;
import org.kartishev.voltage.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PublicNewsResource {

    private final Logger log = LoggerFactory.getLogger(PublicNewsResource.class);

    private static final String ENTITY_NAME = "news";

    private final NewsService newsService;

    public PublicNewsResource(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    @Timed
    public ResponseEntity<List<NewsDTO>> getAllNews(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of News");
        Page<NewsDTO> page = newsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/public/api/news");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/news/{id}")
    @Timed
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long id) {
        log.debug("REST request to get News : {}", id);
        NewsDTO newsDTO = newsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(newsDTO));
    }



}
