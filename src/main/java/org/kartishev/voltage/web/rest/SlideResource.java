package org.kartishev.voltage.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.service.SlideService;
import org.kartishev.voltage.service.dto.SlideDTO;
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
public class SlideResource {

    private final Logger log = LoggerFactory.getLogger(SlideResource.class);

    private static final String ENTITY_NAME = "slide";

    private final SlideService slideService;

    public SlideResource(SlideService slideService) {
        this.slideService = slideService;
    }


    @PostMapping("/slides")
    @Timed
    public ResponseEntity<SlideDTO> createSlide(@Valid @RequestBody SlideDTO slideDTO) throws URISyntaxException {
        if (slideDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new slide cannot already have an ID")).body(null);
        }
        SlideDTO result = slideService.save(slideDTO);
        return ResponseEntity.created(new URI("/api/slides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/slides")
    @Timed
    public ResponseEntity<SlideDTO> updateSlide(@Valid @RequestBody SlideDTO slideDTO) throws URISyntaxException {
        if (slideDTO.getId() == null) {
            return createSlide(slideDTO);
        }
        SlideDTO result = slideService.save(slideDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, slideDTO.getId().toString()))
            .body(result);
    }


    @GetMapping("/slides")
    @Timed
    public ResponseEntity<List<SlideDTO>> getAllSlides(@ApiParam Pageable pageable) {
        Page<SlideDTO> page = slideService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/slides");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/slides/lang/{lang}")
    @Timed
    public ResponseEntity<List<SlideDTO>> getAllSlidesByLanguage(@PathVariable String lang, @ApiParam Pageable pageable) {
        Page<SlideDTO> page = slideService.findAllByLanguage(Language.getByShortName(lang), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/slides/{id}")
    @Timed
    public ResponseEntity<SlideDTO> getSlide(@PathVariable Long id) {
        SlideDTO slideDTO = slideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(slideDTO));
    }


    @DeleteMapping("/slides/{id}")
    @Timed
    public ResponseEntity<Void> deleteSlide(@PathVariable Long id) {
        slideService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
