package org.kartishev.voltage.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kartishev.voltage.VoltageApp;
import org.kartishev.voltage.domain.BlogCategory;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.repository.BlogCategoryRepository;
import org.kartishev.voltage.service.BlogCategoryService;
import org.kartishev.voltage.service.dto.BlogCategoryDTO;
import org.kartishev.voltage.service.mapper.BlogCategoryMapper;
import org.kartishev.voltage.web.rest.errors.ExceptionTranslator;
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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.kartishev.voltage.web.rest.TestUtil.sameInstant;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for the BlogCategoryResource REST controller.
 *
 * @see BlogCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoltageApp.class)
public class BlogCategoryResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.RUSSIAN;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @Autowired
    private BlogCategoryMapper blogCategoryMapper;

    @Autowired
    private BlogCategoryService blogCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBlogCategoryMockMvc;

    private BlogCategory blogCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BlogCategoryResource blogCategoryResource = new BlogCategoryResource(blogCategoryService);
        this.restBlogCategoryMockMvc = MockMvcBuilders.standaloneSetup(blogCategoryResource)
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
    public static BlogCategory createEntity(EntityManager em) {
        BlogCategory blogCategory = new BlogCategory()
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED)
            .version(DEFAULT_VERSION)
            .title(DEFAULT_TITLE)
            .language(DEFAULT_LANGUAGE);
        return blogCategory;
    }

    @Before
    public void initTest() {
        blogCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlogCategory() throws Exception {
        int databaseSizeBeforeCreate = blogCategoryRepository.findAll().size();

        // Create the BlogCategory
        BlogCategoryDTO blogCategoryDTO = blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory);
        restBlogCategoryMockMvc.perform(post("/api/blog-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the BlogCategory in the database
        List<BlogCategory> blogCategoryList = blogCategoryRepository.findAll();
        assertThat(blogCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        BlogCategory testBlogCategory = blogCategoryList.get(blogCategoryList.size() - 1);
        assertThat(testBlogCategory.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testBlogCategory.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testBlogCategory.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testBlogCategory.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBlogCategory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

    }

    @Test
    @Transactional
    public void createBlogCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blogCategoryRepository.findAll().size();

        // Create the BlogCategory with an existing ID
        blogCategory.setId(1L);
        BlogCategoryDTO blogCategoryDTO = blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogCategoryMockMvc.perform(post("/api/blog-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BlogCategory> blogCategoryList = blogCategoryRepository.findAll();
        assertThat(blogCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogCategoryRepository.findAll().size();
        // set the field null
        blogCategory.setTitle(null);

        // Create the BlogCategory, which fails.
        BlogCategoryDTO blogCategoryDTO = blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory);

        restBlogCategoryMockMvc.perform(post("/api/blog-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<BlogCategory> blogCategoryList = blogCategoryRepository.findAll();
        assertThat(blogCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogCategoryRepository.findAll().size();
        // set the field null
        blogCategory.setLanguage(null);

        // Create the BlogCategory, which fails.
        BlogCategoryDTO blogCategoryDTO = blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory);

        restBlogCategoryMockMvc.perform(post("/api/blog-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<BlogCategory> blogCategoryList = blogCategoryRepository.findAll();
        assertThat(blogCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBlogCategories() throws Exception {
        // Initialize the database
        blogCategoryRepository.saveAndFlush(blogCategory);

        // Get all the blogCategoryList
        restBlogCategoryMockMvc.perform(get("/api/blog-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getBlogCategory() throws Exception {
        // Initialize the database
        blogCategoryRepository.saveAndFlush(blogCategory);

        // Get the blogCategory
        restBlogCategoryMockMvc.perform(get("/api/blog-categories/{id}", blogCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blogCategory.getId().intValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBlogCategory() throws Exception {
        // Get the blogCategory
        restBlogCategoryMockMvc.perform(get("/api/blog-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlogCategory() throws Exception {
        // Initialize the database
        blogCategoryRepository.saveAndFlush(blogCategory);
        int databaseSizeBeforeUpdate = blogCategoryRepository.findAll().size();

        // Update the blogCategory
        BlogCategory updatedBlogCategory = blogCategoryRepository.findOne(blogCategory.getId());
        updatedBlogCategory
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED)
            .version(UPDATED_VERSION)
            .title(UPDATED_TITLE)
            .language(UPDATED_LANGUAGE);
        BlogCategoryDTO blogCategoryDTO = blogCategoryMapper.blogCategoryToBlogCategoryDTO(updatedBlogCategory);

        restBlogCategoryMockMvc.perform(put("/api/blog-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the BlogCategory in the database
        List<BlogCategory> blogCategoryList = blogCategoryRepository.findAll();
        assertThat(blogCategoryList).hasSize(databaseSizeBeforeUpdate);
        BlogCategory testBlogCategory = blogCategoryList.get(blogCategoryList.size() - 1);
        assertThat(testBlogCategory.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testBlogCategory.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testBlogCategory.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testBlogCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBlogCategory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

    }

    @Test
    @Transactional
    public void updateNonExistingBlogCategory() throws Exception {
        int databaseSizeBeforeUpdate = blogCategoryRepository.findAll().size();

        // Create the BlogCategory
        BlogCategoryDTO blogCategoryDTO = blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBlogCategoryMockMvc.perform(put("/api/blog-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the BlogCategory in the database
        List<BlogCategory> blogCategoryList = blogCategoryRepository.findAll();
        assertThat(blogCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBlogCategory() throws Exception {
        // Initialize the database
        blogCategoryRepository.saveAndFlush(blogCategory);
        int databaseSizeBeforeDelete = blogCategoryRepository.findAll().size();

        // Get the blogCategory
        restBlogCategoryMockMvc.perform(delete("/api/blog-categories/{id}", blogCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BlogCategory> blogCategoryList = blogCategoryRepository.findAll();
        assertThat(blogCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogCategory.class);
    }
}
