package org.kartishev.voltage.service.mapper;

import org.kartishev.voltage.domain.*;
import org.kartishev.voltage.service.dto.NewsDTO;

import org.mapstruct.*;
import java.util.List;


@Mapper(componentModel = "spring", uses = {})
public interface NewsMapper {

    NewsDTO newsToNewsDTO(News news);

    List<NewsDTO> newsToNewsDTOs(List<News> news);

}
