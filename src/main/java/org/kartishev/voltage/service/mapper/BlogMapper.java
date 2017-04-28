package org.kartishev.voltage.service.mapper;

import org.kartishev.voltage.domain.*;
import org.kartishev.voltage.service.dto.BlogDTO;

import org.mapstruct.*;
import java.util.List;


@Mapper(componentModel = "spring", uses = {BlogCategoryMapper.class, })
public interface BlogMapper {

    BlogDTO blogToBlogDTO(Blog blog);

    List<BlogDTO> blogsToBlogDTOs(List<Blog> blogs);

}
