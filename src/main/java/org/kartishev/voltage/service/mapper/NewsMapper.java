package org.kartishev.voltage.service.mapper;

import org.kartishev.voltage.domain.*;
import org.kartishev.voltage.service.dto.NewsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity News and its DTO NewsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NewsMapper {

    NewsDTO newsToNewsDTO(News news);

    List<NewsDTO> newsToNewsDTOs(List<News> news);

    News newsDTOToNews(NewsDTO newsDTO);

    List<News> newsDTOsToNews(List<NewsDTO> newsDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default News newsFromId(Long id) {
        if (id == null) {
            return null;
        }
        News news = new News();
        news.setId(id);
        return news;
    }
    

}
