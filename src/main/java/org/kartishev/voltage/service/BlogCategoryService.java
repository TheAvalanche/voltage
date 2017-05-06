package org.kartishev.voltage.service;

import org.kartishev.voltage.domain.BlogCategory;
import org.kartishev.voltage.domain.enumeration.Language;
import org.kartishev.voltage.repository.BlogCategoryRepository;
import org.kartishev.voltage.service.dto.BlogCategoryDTO;
import org.kartishev.voltage.service.mapper.BlogCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogCategoryService {

    private final Logger log = LoggerFactory.getLogger(BlogCategoryService.class);

    private final BlogCategoryRepository blogCategoryRepository;

    private final BlogCategoryMapper blogCategoryMapper;


    public BlogCategoryService(BlogCategoryRepository blogCategoryRepository, BlogCategoryMapper blogCategoryMapper) {
        this.blogCategoryRepository = blogCategoryRepository;
        this.blogCategoryMapper = blogCategoryMapper;
    }

    public BlogCategoryDTO save(BlogCategoryDTO blogCategoryDTO) {
        BlogCategory blogCategory = findOrCreateBlogCategory(blogCategoryDTO);
        blogCategory.setTitle(blogCategoryDTO.getTitle());
        blogCategory.setLanguage(blogCategoryDTO.getLanguage());

        blogCategory = blogCategoryRepository.save(blogCategory);
        return blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory);
    }

    private BlogCategory findOrCreateBlogCategory(BlogCategoryDTO blogCategoryDTO) {
        BlogCategory blogCategory;
        if (blogCategoryDTO.getId() != null) {
            blogCategory = blogCategoryRepository.getOne(blogCategoryDTO.getId());
        } else {
            blogCategory = new BlogCategory();
        }
        return blogCategory;
    }

    @Transactional(readOnly = true)
    public List<BlogCategoryDTO> findAll() {
        List<BlogCategory> result = blogCategoryRepository.findAll();
        return blogCategoryMapper.blogCategoriesToBlogCategoryDTOs(result);
    }

    @Transactional(readOnly = true)
    public Page<BlogCategoryDTO> findAll(Pageable pageable) {
        Page<BlogCategory> result = blogCategoryRepository.findAll(pageable);
        return result.map(blogCategoryMapper::blogCategoryToBlogCategoryDTO);
    }

    @Transactional(readOnly = true)
    public List<BlogCategoryDTO> findAllByLanguage(Language language) {
        List<BlogCategory> result = blogCategoryRepository.findAllByLanguage(language);
        return blogCategoryMapper.blogCategoriesToBlogCategoryDTOs(result);
    }

    @Transactional(readOnly = true)
    public Page<BlogCategoryDTO> findAllByLanguage(Language language, Pageable pageable) {
        Page<BlogCategory> result = blogCategoryRepository.findAllByLanguage(language, pageable);
        return result.map(blogCategoryMapper::blogCategoryToBlogCategoryDTO);
    }

    @Transactional(readOnly = true)
    public BlogCategoryDTO findOne(Long id) {
        BlogCategory blogCategory = blogCategoryRepository.findOne(id);
        return blogCategoryMapper.blogCategoryToBlogCategoryDTO(blogCategory);
    }

    public void delete(Long id) {
        blogCategoryRepository.delete(id);
    }
}
