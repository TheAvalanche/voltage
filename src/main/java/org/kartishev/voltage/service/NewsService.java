package org.kartishev.voltage.service;

import org.kartishev.voltage.domain.News;
import org.kartishev.voltage.repository.NewsRepository;
import org.kartishev.voltage.repository.search.NewsSearchRepository;
import org.kartishev.voltage.service.dto.NewsDTO;
import org.kartishev.voltage.service.mapper.NewsMapper;
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
 * Service Implementation for managing News.
 */
@Service
@Transactional
public class NewsService {

    private final Logger log = LoggerFactory.getLogger(NewsService.class);
    
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    private final NewsSearchRepository newsSearchRepository;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper, NewsSearchRepository newsSearchRepository) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.newsSearchRepository = newsSearchRepository;
    }

    /**
     * Save a news.
     *
     * @param newsDTO the entity to save
     * @return the persisted entity
     */
    public NewsDTO save(NewsDTO newsDTO) {
        log.debug("Request to save News : {}", newsDTO);
        News news = newsMapper.newsDTOToNews(newsDTO);
        news = newsRepository.save(news);
        NewsDTO result = newsMapper.newsToNewsDTO(news);
        newsSearchRepository.save(news);
        return result;
    }

    /**
     *  Get all the news.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NewsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all News");
        Page<News> result = newsRepository.findAll(pageable);
        return result.map(news -> newsMapper.newsToNewsDTO(news));
    }

    /**
     *  Get one news by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public NewsDTO findOne(Long id) {
        log.debug("Request to get News : {}", id);
        News news = newsRepository.findOne(id);
        NewsDTO newsDTO = newsMapper.newsToNewsDTO(news);
        return newsDTO;
    }

    /**
     *  Delete the  news by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete News : {}", id);
        newsRepository.delete(id);
        newsSearchRepository.delete(id);
    }

    /**
     * Search for the news corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NewsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of News for query {}", query);
        Page<News> result = newsSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(news -> newsMapper.newsToNewsDTO(news));
    }
}
