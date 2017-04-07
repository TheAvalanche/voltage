package org.kartishev.voltage.service.mapper;

import org.kartishev.voltage.domain.*;
import org.kartishev.voltage.service.dto.BlogCategoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BlogCategory and its DTO BlogCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BlogCategoryMapper {

    BlogCategoryDTO blogCategoryToBlogCategoryDTO(BlogCategory blogCategory);

    List<BlogCategoryDTO> blogCategoriesToBlogCategoryDTOs(List<BlogCategory> blogCategories);

    BlogCategory blogCategoryDTOToBlogCategory(BlogCategoryDTO blogCategoryDTO);

    List<BlogCategory> blogCategoryDTOsToBlogCategories(List<BlogCategoryDTO> blogCategoryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default BlogCategory blogCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setId(id);
        return blogCategory;
    }
    

}
