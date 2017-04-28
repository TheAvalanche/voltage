package org.kartishev.voltage.service;

import org.kartishev.voltage.domain.News;
import org.kartishev.voltage.repository.NewsRepository;
import org.kartishev.voltage.service.dto.NewsDTO;
import org.kartishev.voltage.service.mapper.NewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NewsService {

    private final Logger log = LoggerFactory.getLogger(NewsService.class);

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    public NewsDTO save(NewsDTO newsDTO) {
        log.debug("Request to save News : {}", newsDTO);
        News news;
        if (newsDTO.getId() != null) {
            news = newsRepository.getOne(newsDTO.getId());
        } else {
            news = new News();
        }
        news.setTitle(newsDTO.getTitle());
        news.setBody(newsDTO.getBody());
        news.setImage(newsDTO.getImage());
        news.setImageContentType(newsDTO.getImageContentType());
        news.setLanguage(newsDTO.getLanguage());

        news = newsRepository.save(news);
        return newsMapper.newsToNewsDTO(news);
    }

    @Transactional(readOnly = true)
    public Page<NewsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all News");
        Page<News> result = newsRepository.findAll(pageable);
        return result.map(newsMapper::newsToNewsDTO);
    }

    @Transactional(readOnly = true)
    public NewsDTO findOne(Long id) {
        log.debug("Request to get News : {}", id);
        News news = newsRepository.findOne(id);
        NewsDTO newsDTO = newsMapper.newsToNewsDTO(news);
        return newsDTO;
    }

    public void delete(Long id) {
        log.debug("Request to delete News : {}", id);
        newsRepository.delete(id);
    }
}
