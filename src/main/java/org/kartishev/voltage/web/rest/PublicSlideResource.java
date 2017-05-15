package org.kartishev.voltage.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.service.SlideService;
import org.kartishev.voltage.service.dto.SlideDTO;
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
public class PublicSlideResource {

    private final Logger log = LoggerFactory.getLogger(PublicSlideResource.class);

    private static final String ENTITY_NAME = "slide";

    private final SlideService slideService;

    public PublicSlideResource(SlideService slideService) {
        this.slideService = slideService;
    }


    @GetMapping("/slides")
    @Timed
    public ResponseEntity<List<SlideDTO>> getAllSlides() {
        List<SlideDTO> result = slideService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/slides/lang/{lang}")
    @Timed
    public ResponseEntity<List<SlideDTO>> getAllSlidesByLanguage(@PathVariable String lang) {
        List<SlideDTO> result = slideService.findAllByLanguage(Language.getByShortName(lang));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/slides/{id}")
    @Timed
    public ResponseEntity<SlideDTO> getSlide(@PathVariable Long id) {
        SlideDTO result = slideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

}
