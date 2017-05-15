package org.kartishev.voltage.service;

import org.kartishev.voltage.domain.AppProperty;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.repository.AppPropertyRepository;
import org.kartishev.voltage.service.dto.AppPropertyDTO;
import org.kartishev.voltage.service.mapper.AppPropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppPropertyService {

    private final Logger log = LoggerFactory.getLogger(AppPropertyService.class);

    private final AppPropertyRepository appPropertyRepository;

    private final AppPropertyMapper appPropertyMapper;

    public AppPropertyService(AppPropertyRepository appPropertyRepository, AppPropertyMapper appPropertyMapper) {
        this.appPropertyRepository = appPropertyRepository;
        this.appPropertyMapper = appPropertyMapper;
    }

    public AppPropertyDTO save(AppPropertyDTO appPropertyDTO) {
        AppProperty appProperty = findOrCreateAppProperty(appPropertyDTO);
        appProperty.setName(appPropertyDTO.getName());
        appProperty.setValue(appPropertyDTO.getValue());
        appProperty.setPropertyType(appPropertyDTO.getPropertyType());
        appProperty.setLanguage(appPropertyDTO.getLanguage());

        appProperty = appPropertyRepository.save(appProperty);
        return appPropertyMapper.appPropertyToAppPropertyDTO(appProperty);
    }

    private AppProperty findOrCreateAppProperty(AppPropertyDTO appPropertyDTO) {
        AppProperty appProperty;
        if (appPropertyDTO.getId() != null) {
            appProperty = appPropertyRepository.getOne(appPropertyDTO.getId());
        } else {
            appProperty = new AppProperty();
        }
        return appProperty;
    }

    @Transactional(readOnly = true)
    public List<AppPropertyDTO> findAll() {
        List<AppProperty> result = appPropertyRepository.findAll();
        return appPropertyMapper.appPropertiesToAppPropertyDTOs(result);
    }

    @Transactional(readOnly = true)
    public Page<AppPropertyDTO> findAll(Pageable pageable) {
        Page<AppProperty> result = appPropertyRepository.findAll(pageable);
        return result.map(appPropertyMapper::appPropertyToAppPropertyDTO);
    }

    @Transactional(readOnly = true)
    public List<AppPropertyDTO> findAllByLanguage(Language language) {
        List<AppProperty> result = appPropertyRepository.findAllByLanguage(language);
        return appPropertyMapper.appPropertiesToAppPropertyDTOs(result);
    }

    @Transactional(readOnly = true)
    public Page<AppPropertyDTO> findAllByLanguage(Language language, Pageable pageable) {
        Page<AppProperty> result = appPropertyRepository.findAllByLanguage(language, pageable);
        return result.map(appPropertyMapper::appPropertyToAppPropertyDTO);
    }

    @Transactional(readOnly = true)
    public AppPropertyDTO findOne(Long id) {
        AppProperty appProperty = appPropertyRepository.findOne(id);
        return appPropertyMapper.appPropertyToAppPropertyDTO(appProperty);
    }

    public void delete(Long id) {
        appPropertyRepository.delete(id);
    }
}
