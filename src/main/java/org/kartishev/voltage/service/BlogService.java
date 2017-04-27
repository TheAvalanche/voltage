package org.kartishev.voltage.service;

import org.kartishev.voltage.domain.Blog;
import org.kartishev.voltage.repository.BlogRepository;
import org.kartishev.voltage.service.dto.BlogDTO;
import org.kartishev.voltage.service.mapper.BlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;


    public BlogService(BlogRepository blogRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
    }

    public BlogDTO save(BlogDTO blogDTO) {
        log.debug("Request to save Blog : {}", blogDTO);
        Blog blog = blogMapper.blogDTOToBlog(blogDTO);
        blog = blogRepository.save(blog);
        BlogDTO result = blogMapper.blogToBlogDTO(blog);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<BlogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Blogs");
        Page<Blog> result = blogRepository.findAll(pageable);
        return result.map(blog -> blogMapper.blogToBlogDTO(blog));
    }

    @Transactional(readOnly = true)
    public BlogDTO findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        Blog blog = blogRepository.findOneWithEagerRelationships(id);
        BlogDTO blogDTO = blogMapper.blogToBlogDTO(blog);
        return blogDTO;
    }

    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.delete(id);
    }

}
