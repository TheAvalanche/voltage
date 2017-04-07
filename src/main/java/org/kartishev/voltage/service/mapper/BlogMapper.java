package org.kartishev.voltage.service.mapper;

import org.kartishev.voltage.domain.*;
import org.kartishev.voltage.service.dto.BlogDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Blog and its DTO BlogDTO.
 */
@Mapper(componentModel = "spring", uses = {BlogCategoryMapper.class, })
public interface BlogMapper {

    BlogDTO blogToBlogDTO(Blog blog);

    List<BlogDTO> blogsToBlogDTOs(List<Blog> blogs);

    Blog blogDTOToBlog(BlogDTO blogDTO);

    List<Blog> blogDTOsToBlogs(List<BlogDTO> blogDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Blog blogFromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
    

}
