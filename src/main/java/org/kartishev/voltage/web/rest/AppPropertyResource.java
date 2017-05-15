package org.kartishev.voltage.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.service.AppPropertyService;
import org.kartishev.voltage.service.dto.AppPropertyDTO;
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
public class AppPropertyResource {

    private final Logger log = LoggerFactory.getLogger(AppPropertyResource.class);

    private static final String ENTITY_NAME = "appProperty";

    private final AppPropertyService appPropertyService;

    public AppPropertyResource(AppPropertyService appPropertyService) {
        this.appPropertyService = appPropertyService;
    }


    @PostMapping("/app-properties")
    @Timed
    public ResponseEntity<AppPropertyDTO> createAppProperty(@Valid @RequestBody AppPropertyDTO appPropertyDTO) throws URISyntaxException {
        if (appPropertyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new appProperty cannot already have an ID")).body(null);
        }
        AppPropertyDTO result = appPropertyService.save(appPropertyDTO);
        return ResponseEntity.created(new URI("/api/app-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/app-properties")
    @Timed
    public ResponseEntity<AppPropertyDTO> updateAppProperty(@Valid @RequestBody AppPropertyDTO appPropertyDTO) throws URISyntaxException {
        if (appPropertyDTO.getId() == null) {
            return createAppProperty(appPropertyDTO);
        }
        AppPropertyDTO result = appPropertyService.save(appPropertyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appPropertyDTO.getId().toString()))
            .body(result);
    }


    @GetMapping("/app-properties")
    @Timed
    public ResponseEntity<List<AppPropertyDTO>> getAllAppProperties(@ApiParam Pageable pageable) {
        Page<AppPropertyDTO> page = appPropertyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/app-properties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/app-properties/lang/{lang}")
    @Timed
    public ResponseEntity<List<AppPropertyDTO>> getAllAppPropertiesByLanguage(@PathVariable String lang, @ApiParam Pageable pageable) {
        Page<AppPropertyDTO> page = appPropertyService.findAllByLanguage(Language.getByShortName(lang), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/app-properties/{id}")
    @Timed
    public ResponseEntity<AppPropertyDTO> getAppProperty(@PathVariable Long id) {
        AppPropertyDTO appPropertyDTO = appPropertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appPropertyDTO));
    }


    @DeleteMapping("/app-properties/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppProperty(@PathVariable Long id) {
        appPropertyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
