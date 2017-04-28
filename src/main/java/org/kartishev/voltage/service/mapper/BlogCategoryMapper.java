package org.kartishev.voltage.service.mapper;

import org.kartishev.voltage.domain.*;
import org.kartishev.voltage.service.dto.BlogCategoryDTO;

import org.mapstruct.*;
import java.util.List;


@Mapper(componentModel = "spring", uses = {})
public interface BlogCategoryMapper {

    BlogCategoryDTO blogCategoryToBlogCategoryDTO(BlogCategory blogCategory);

    List<BlogCategoryDTO> blogCategoriesToBlogCategoryDTOs(List<BlogCategory> blogCategories);

}
