package org.kartishev.voltage.service;

import org.kartishev.voltage.domain.Blog;
import org.kartishev.voltage.repository.BlogRepository;
import org.kartishev.voltage.repository.search.BlogSearchRepository;
import org.kartishev.voltage.service.dto.BlogDTO;
import org.kartishev.voltage.service.mapper.BlogMapper;
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
 * Service Implementation for managing Blog.
 */
@Service
@Transactional
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);
    
    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;

    private final BlogSearchRepository blogSearchRepository;

    public BlogService(BlogRepository blogRepository, BlogMapper blogMapper, BlogSearchRepository blogSearchRepository) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
        this.blogSearchRepository = blogSearchRepository;
    }

    /**
     * Save a blog.
     *
     * @param blogDTO the entity to save
     * @return the persisted entity
     */
    public BlogDTO save(BlogDTO blogDTO) {
        log.debug("Request to save Blog : {}", blogDTO);
        Blog blog = blogMapper.blogDTOToBlog(blogDTO);
        blog = blogRepository.save(blog);
        BlogDTO result = blogMapper.blogToBlogDTO(blog);
        blogSearchRepository.save(blog);
        return result;
    }

    /**
     *  Get all the blogs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BlogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Blogs");
        Page<Blog> result = blogRepository.findAll(pageable);
        return result.map(blog -> blogMapper.blogToBlogDTO(blog));
    }

    /**
     *  Get one blog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BlogDTO findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        Blog blog = blogRepository.findOneWithEagerRelationships(id);
        BlogDTO blogDTO = blogMapper.blogToBlogDTO(blog);
        return blogDTO;
    }

    /**
     *  Delete the  blog by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.delete(id);
        blogSearchRepository.delete(id);
    }

    /**
     * Search for the blog corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BlogDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Blogs for query {}", query);
        Page<Blog> result = blogSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(blog -> blogMapper.blogToBlogDTO(blog));
    }
}
