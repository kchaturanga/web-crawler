package com.kasunc.webcrawler.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kasunc.webcrawler.model.jpa.CrawlURL;

public interface CrawlURLRepository extends JpaRepository<CrawlURL, Long> {
	
	List<CrawlURL> findByCreawleURLIs(String urlString);
	List<CrawlURL> findByUrlContentContaining(String query);
	
	@Query("select u from CrawlURL u where u.crawlStatus = 'N' and  u.nestediterations <= :nestedIterations order by u.id  ")
	 List<CrawlURL> findFirstPendingURLLesserThanNestedLimit(Pageable pageable ,@Param("nestedIterations") Integer nestedIterations);
	
	@Query("select u from CrawlURL u where u.crawlStatus = 'N' and  u.creawleURL = :crurl  order by u.id  ")
	 List<CrawlURL> findFirstPendingURLEqulasToNestedLimit(  @Param("crurl") String crawleURL);
	

}
