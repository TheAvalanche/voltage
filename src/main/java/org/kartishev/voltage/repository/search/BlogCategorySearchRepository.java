package org.kartishev.voltage.repository.search;

import org.kartishev.voltage.domain.BlogCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BlogCategory entity.
 */
public interface BlogCategorySearchRepository extends ElasticsearchRepository<BlogCategory, Long> {
}
