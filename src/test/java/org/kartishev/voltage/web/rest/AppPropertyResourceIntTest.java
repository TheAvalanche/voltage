package org.kartishev.voltage.web.rest;

import org.kartishev.voltage.VoltageApp;

import org.kartishev.voltage.domain.AppProperty;
import org.kartishev.voltage.repository.AppPropertyRepository;
import org.kartishev.voltage.service.AppPropertyService;
import org.kartishev.voltage.service.dto.AppPropertyDTO;
import org.kartishev.voltage.service.mapper.AppPropertyMapper;
import org.kartishev.voltage.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.kartishev.voltage.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.kartishev.voltage.domain.enumeration.AppPropertyType;
import org.kartishev.voltage.domain.enumeration.Language;
/**
 * Test class for the AppPropertyResource REST controller.
 *
 * @see AppPropertyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoltageApp.class)
public class AppPropertyResourceIntTest {

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final AppPropertyType DEFAULT_TYPE = AppPropertyType.CONTACT;
    private static final AppPropertyType UPDATED_TYPE = AppPropertyType.SOCIAL;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.RUSSIAN;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private AppPropertyRepository appPropertyRepository;

    @Autowired
    private AppPropertyMapper appPropertyMapper;

    @Autowired
    private AppPropertyService appPropertyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppPropertyMockMvc;

    private AppProperty appProperty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppPropertyResource appPropertyResource = new AppPropertyResource(appPropertyService);
        this.restAppPropertyMockMvc = MockMvcBuilders.standaloneSetup(appPropertyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppProperty createEntity(EntityManager em) {
        AppProperty appProperty = new AppProperty()
            .version(DEFAULT_VERSION)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED)
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .language(DEFAULT_LANGUAGE);
        return appProperty;
    }

    @Before
    public void initTest() {
        appProperty = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppProperty() throws Exception {
        int databaseSizeBeforeCreate = appPropertyRepository.findAll().size();

        // Create the AppProperty
        AppPropertyDTO appPropertyDTO = appPropertyMapper.appPropertyToAppPropertyDTO(appProperty);
        restAppPropertyMockMvc.perform(post("/api/app-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appPropertyDTO)))
            .andExpect(status().isCreated());

        // Validate the AppProperty in the database
        List<AppProperty> appPropertyList = appPropertyRepository.findAll();
        assertThat(appPropertyList).hasSize(databaseSizeBeforeCreate + 1);
        AppProperty testAppProperty = appPropertyList.get(appPropertyList.size() - 1);
        assertThat(testAppProperty.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAppProperty.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAppProperty.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testAppProperty.getPropertyType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAppProperty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppProperty.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAppProperty.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createAppPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appPropertyRepository.findAll().size();

        // Create the AppProperty with an existing ID
        appProperty.setId(1L);
        AppPropertyDTO appPropertyDTO = appPropertyMapper.appPropertyToAppPropertyDTO(appProperty);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppPropertyMockMvc.perform(post("/api/app-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appPropertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AppProperty> appPropertyList = appPropertyRepository.findAll();
        assertThat(appPropertyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appPropertyRepository.findAll().size();
        // set the field null
        appProperty.setName(null);

        // Create the AppProperty, which fails.
        AppPropertyDTO appPropertyDTO = appPropertyMapper.appPropertyToAppPropertyDTO(appProperty);

        restAppPropertyMockMvc.perform(post("/api/app-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appPropertyDTO)))
            .andExpect(status().isBadRequest());

        List<AppProperty> appPropertyList = appPropertyRepository.findAll();
        assertThat(appPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = appPropertyRepository.findAll().size();
        // set the field null
        appProperty.setValue(null);

        // Create the AppProperty, which fails.
        AppPropertyDTO appPropertyDTO = appPropertyMapper.appPropertyToAppPropertyDTO(appProperty);

        restAppPropertyMockMvc.perform(post("/api/app-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appPropertyDTO)))
            .andExpect(status().isBadRequest());

        List<AppProperty> appPropertyList = appPropertyRepository.findAll();
        assertThat(appPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppProperties() throws Exception {
        // Initialize the database
        appPropertyRepository.saveAndFlush(appProperty);

        // Get all the appPropertyList
        restAppPropertyMockMvc.perform(get("/api/app-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appProperty.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getAppProperty() throws Exception {
        // Initialize the database
        appPropertyRepository.saveAndFlush(appProperty);

        // Get the appProperty
        restAppPropertyMockMvc.perform(get("/api/app-properties/{id}", appProperty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appProperty.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppProperty() throws Exception {
        // Get the appProperty
        restAppPropertyMockMvc.perform(get("/api/app-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppProperty() throws Exception {
        // Initialize the database
        appPropertyRepository.saveAndFlush(appProperty);
        int databaseSizeBeforeUpdate = appPropertyRepository.findAll().size();

        // Update the appProperty
        AppProperty updatedAppProperty = appPropertyRepository.findOne(appProperty.getId());
        updatedAppProperty
            .version(UPDATED_VERSION)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .language(UPDATED_LANGUAGE);
        AppPropertyDTO appPropertyDTO = appPropertyMapper.appPropertyToAppPropertyDTO(updatedAppProperty);

        restAppPropertyMockMvc.perform(put("/api/app-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appPropertyDTO)))
            .andExpect(status().isOk());

        // Validate the AppProperty in the database
        List<AppProperty> appPropertyList = appPropertyRepository.findAll();
        assertThat(appPropertyList).hasSize(databaseSizeBeforeUpdate);
        AppProperty testAppProperty = appPropertyList.get(appPropertyList.size() - 1);
        assertThat(testAppProperty.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAppProperty.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAppProperty.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testAppProperty.getPropertyType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAppProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppProperty.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAppProperty.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingAppProperty() throws Exception {
        int databaseSizeBeforeUpdate = appPropertyRepository.findAll().size();

        // Create the AppProperty
        AppPropertyDTO appPropertyDTO = appPropertyMapper.appPropertyToAppPropertyDTO(appProperty);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppPropertyMockMvc.perform(put("/api/app-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appPropertyDTO)))
            .andExpect(status().isCreated());

        // Validate the AppProperty in the database
        List<AppProperty> appPropertyList = appPropertyRepository.findAll();
        assertThat(appPropertyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppProperty() throws Exception {
        // Initialize the database
        appPropertyRepository.saveAndFlush(appProperty);
        int databaseSizeBeforeDelete = appPropertyRepository.findAll().size();

        // Get the appProperty
        restAppPropertyMockMvc.perform(delete("/api/app-properties/{id}", appProperty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppProperty> appPropertyList = appPropertyRepository.findAll();
        assertThat(appPropertyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppProperty.class);
    }
}
