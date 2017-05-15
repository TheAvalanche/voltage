package org.kartishev.voltage.service;

import org.kartishev.voltage.domain.Slide;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.repository.SlideRepository;
import org.kartishev.voltage.service.dto.SlideDTO;
import org.kartishev.voltage.service.mapper.SlideMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SlideService {

    private final Logger log = LoggerFactory.getLogger(SlideService.class);

    private final SlideRepository slideRepository;

    private final SlideMapper slideMapper;

    public SlideService(SlideRepository slideRepository, SlideMapper slideMapper) {
        this.slideRepository = slideRepository;
        this.slideMapper = slideMapper;
    }

    public SlideDTO save(SlideDTO slideDTO) {
        Slide slide = findOrCreateAppProperty(slideDTO);
        slide.setTitle(slideDTO.getTitle());
        slide.setDescription(slideDTO.getDescription());
        slide.setImageUrl(slideDTO.getImageUrl());
        slide.setLanguage(slideDTO.getLanguage());

        slide = slideRepository.save(slide);
        return slideMapper.slideToSlideDTO(slide);
    }

    private Slide findOrCreateAppProperty(SlideDTO slideDTO) {
        Slide slide;
        if (slideDTO.getId() != null) {
            slide = slideRepository.getOne(slideDTO.getId());
        } else {
            slide = new Slide();
        }
        return slide;
    }

    @Transactional(readOnly = true)
    public List<SlideDTO> findAll() {
        List<Slide> result = slideRepository.findAll();
        return slideMapper.slidesToSlideDTOs(result);
    }

    @Transactional(readOnly = true)
    public Page<SlideDTO> findAll(Pageable pageable) {
        Page<Slide> result = slideRepository.findAll(pageable);
        return result.map(slideMapper::slideToSlideDTO);
    }

    @Transactional(readOnly = true)
    public List<SlideDTO> findAllByLanguage(Language language) {
        List<Slide> result = slideRepository.findAllByLanguage(language);
        return slideMapper.slidesToSlideDTOs(result);
    }

    @Transactional(readOnly = true)
    public Page<SlideDTO> findAllByLanguage(Language language, Pageable pageable) {
        Page<Slide> result = slideRepository.findAllByLanguage(language, pageable);
        return result.map(slideMapper::slideToSlideDTO);
    }

    @Transactional(readOnly = true)
    public SlideDTO findOne(Long id) {
        Slide slide = slideRepository.findOne(id);
        return slideMapper.slideToSlideDTO(slide);
    }

    public void delete(Long id) {
        slideRepository.delete(id);
    }
}
