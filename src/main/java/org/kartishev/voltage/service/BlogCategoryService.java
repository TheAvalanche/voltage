package org.kartishev.voltage.service;

import org.kartishev.voltage.domain.BlogCategory;
import org.kartishev.voltage.repository.BlogCategoryRepository;
import org.kartishev.voltage.repository.search.BlogCategorySearchRepository;
import org.kartishev.voltage.service.dto.BlogCategoryDTO;
import org.kartishev.voltage.service.mapper.BlogCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BlogCategory.
 */
@Service
@Transactional
public class BlogCategoryService {

    private final Logger log = LoggerFactory.getLogger(BlogCategoryService.class);
    
    private final BlogCategoryRepository blogCategoryRepository;

    private final BlogCategoryMapper blogCategoryMapper;

    private final BlogCategorySearchRepository blogCategorySearchRepository;

    public BlogCategoryService(BlogCategoryRepository blogCategoryRepository, BlogCategoryMapper blogCategoryMapper, BlogCategorySearchRepository blogCategorySearchRepository) {
        this.blogCategoryRepository = blogCategoryRepository;
        this.blogCategoryMapper = blogCategoryMapper;
        this.blogCategorySearchRepository = blogCategorySearchRepository;
    }

    /**
     * Save a blogCategory.
     *
     * @param blogCategoryDTO the entity to save
     * @return the persisted entity
     */
    public BlogCategoryDTO save(BlogCategoryDTO blogCategoryDTO) {
        log.debug("Request to save BlogCategory : {}", blogCategoryDTO);
        BlogCategory blogCategory = blogCategoryMapper.blogCategoryDTOToBlogCategory(blogCategoryDTO);
        blogCategory = blogCategoryRepository.save(blogCategory);
        BlogCategoryDTO result = blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory);
        blogCategorySearchRepository.save(blogCategory);
        return result;
    }

    /**
     *  Get all the blogCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BlogCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BlogCategories");
        Page<BlogCategory> result = blogCategoryRepository.findAll(pageable);
        return result.map(blogCategory -> blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory));
    }

    /**
     *  Get one blogCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BlogCategoryDTO findOne(Long id) {
        log.debug("Request to get BlogCategory : {}", id);
        BlogCategory blogCategory = blogCategoryRepository.findOne(id);
        BlogCategoryDTO blogCategoryDTO = blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory);
        return blogCategoryDTO;
    }

    /**
     *  Delete the  blogCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BlogCategory : {}", id);
        blogCategoryRepository.delete(id);
        blogCategorySearchRepository.delete(id);
    }

    /**
     * Search for the blogCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BlogCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BlogCategories for query {}", query);
        Page<BlogCategory> result = blogCategorySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(blogCategory -> blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory));
    }
}
