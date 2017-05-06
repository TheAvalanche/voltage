package org.kartishev.voltage.service;

import org.kartishev.voltage.domain.Blog;
import org.kartishev.voltage.domain.BlogCategory;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.repository.BlogCategoryRepository;
import org.kartishev.voltage.repository.BlogRepository;
import org.kartishev.voltage.service.dto.BlogDTO;
import org.kartishev.voltage.service.mapper.BlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    private final BlogRepository blogRepository;

    private final BlogCategoryRepository blogCategoryRepository;

    private final BlogMapper blogMapper;


    public BlogService(BlogRepository blogRepository, BlogCategoryRepository blogCategoryRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogCategoryRepository = blogCategoryRepository;
        this.blogMapper = blogMapper;
    }

    public BlogDTO save(BlogDTO blogDTO) {
        Blog blog = findOrCreateBlog(blogDTO);
        blog.setTitle(blogDTO.getTitle());
        blog.setBody(blogDTO.getBody());
        blog.setLanguage(blogDTO.getLanguage());
        Set<BlogCategory> blogCategories = new HashSet<>();
        blogDTO.getBlogCategories().forEach(
            bc -> blogCategories.add(blogCategoryRepository.findOne(bc.getId()))
        );
        blog.setBlogCategories(blogCategories);

        blog = blogRepository.save(blog);
        return blogMapper.blogToBlogDTO(blog);
    }

    private Blog findOrCreateBlog(BlogDTO blogDTO) {
        Blog blog;
        if (blogDTO.getId() != null) {
            blog = blogRepository.getOne(blogDTO.getId());
        } else {
            blog = new Blog();
        }
        return blog;
    }

    @Transactional(readOnly = true)
    public Page<BlogDTO> findAll(Pageable pageable) {
        Page<Blog> result = blogRepository.findAll(pageable);
        return result.map(blogMapper::blogToBlogDTO);
    }

    @Transactional(readOnly = true)
    public Page<BlogDTO> findAllByLanguage(Language language, Pageable pageable) {
        Page<Blog> result = blogRepository.findAllByLanguage(language, pageable);
        return result.map(blogMapper::blogToBlogDTO);
    }

    @Transactional(readOnly = true)
    public Page<BlogDTO> findAllByBlogCategories(Long blogCategoryId, Pageable pageable) {
        BlogCategory blogCategory = blogCategoryRepository.findOne(blogCategoryId);
        Page<Blog> result = blogRepository.findAllByBlogCategories(blogCategory, pageable);
        return result.map(blogMapper::blogToBlogDTO);
    }

    @Transactional(readOnly = true)
    public BlogDTO findOne(Long id) {
        Blog blog = blogRepository.findOneWithEagerRelationships(id);
        return blogMapper.blogToBlogDTO(blog);
    }

    public void delete(Long id) {
        blogRepository.delete(id);
    }

}
