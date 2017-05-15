package org.kartishev.voltage.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.service.AppPropertyService;
import org.kartishev.voltage.service.dto.AppPropertyDTO;
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
public class PublicAppPropertyResource {

    private final AppPropertyService appPropertyService;

    public PublicAppPropertyResource(AppPropertyService appPropertyService) {
        this.appPropertyService = appPropertyService;
    }


    @GetMapping("/app-properties")
    @Timed
    public ResponseEntity<List<AppPropertyDTO>> getAllAppProperties() {
        List<AppPropertyDTO> result = appPropertyService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/app-properties/lang/{lang}")
    @Timed
    public ResponseEntity<List<AppPropertyDTO>> getAllAppPropertiesByLanguage(@PathVariable String lang) {
        List<AppPropertyDTO> result = appPropertyService.findAllByLanguage(Language.getByShortName(lang));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/app-properties/{id}")
    @Timed
    public ResponseEntity<AppPropertyDTO> getAppProperty(@PathVariable Long id) {
        AppPropertyDTO result = appPropertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

}
