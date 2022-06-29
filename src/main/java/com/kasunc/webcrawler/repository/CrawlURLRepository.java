package com.kasunc.webcrawler.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kasunc.webcrawler.model.jpa.CrawlURL;

public interface CrawlURLRepository extends JpaRepository<CrawlURL, Long> {
	
	List<CrawlURL> findByCreawleURLIs(String urlString);
	List<CrawlURL> findByUrlContentContaining(String query);
	
	@Query("select u from CrawlURL u where u.crawlStatus = 'N' order by u.id  ")
	List<CrawlURL> findFirstPendingURL(Pageable pageable);

}
