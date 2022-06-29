package com.kasunc.webcrawler.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kasunc.webcrawler.exception.CrawlerException;
import com.kasunc.webcrawler.model.inout.CrawlInput;
import com.kasunc.webcrawler.model.jpa.CrawlURL;
import com.kasunc.webcrawler.model.jpa.UrlEntity;
import com.kasunc.webcrawler.repository.CrawlURLRepository;
import com.kasunc.webcrawler.repository.URLRepository;
import com.kasunc.webcrawler.service.CrawlerService;

@Service
@EnableAsync
public class CrawlerServiceImpl implements CrawlerService {
	Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);

	@Autowired
	URLRepository urlRepository;

	@Autowired
	CrawlURLRepository crawlURLRepository;

	@Value("${max.crawl.count}")
	private Integer maxcrawlcount;

	@Value("${max.crawl.nestediterations}")
	private Integer nestediterations;

	public Integer getNestediterations() {
		return nestediterations;
	}

	public void setNestediterations(Integer nestediterations) {
		this.nestediterations = nestediterations;
	}

	public Integer getMaxcrawlcount() {
		return maxcrawlcount;
	}

	public void setMaxcrawlcount(Integer maxcrawlcount) {
		this.maxcrawlcount = maxcrawlcount;
	}

	@Override
	public CrawlURL crawl(CrawlInput in) throws CrawlerException {

		logger.debug("Start crawl save  => {} ", in.getId());

		Optional<UrlEntity> url = urlRepository.findById(in.getId());

		if (url.isPresent()) {

			logger.debug("UrlEntity found for  => {} ", in.getId());
			if (crawlURLRepository.findByCreawleURLIs(url.get().getUrlString()).isEmpty()) {
				CrawlURL crawlUrl = new CrawlURL(0l, url.get().getUrlString(), url.get().getUrlString(), "", "N",
						"MAIN THREAD");
				crawlURLRepository.save(crawlUrl);

				logger.debug("New  CrawlURL saved   => {} ", in.getId());

				UrlEntity entity = url.get();

				entity.setCrawlledStatus("Y");

				urlRepository.save(entity);

				return crawlUrl;

			} else {

				UrlEntity entity = url.get();

				entity.setCrawlledStatus("Y");

				urlRepository.save(entity);
				
				// Retrieve oldest pending URL to crawl
				List<CrawlURL> s = crawlURLRepository.findFirstPendingURLEqulasToNestedLimit(   url.get().getUrlString()) ;
				for (CrawlURL crawlURL : s) {
					crawlURL.setNestediterations(1);
					crawlURLRepository.save(crawlURL);
					
				}

				throw new CrawlerException("URL already queued for crawling ");
			}

		} else {
			throw new CrawlerException("UrlEntity canot be found ");
		}

	}

	@Async
	@Scheduled(fixedDelay = 500)
	@Override
	public void crawlSchaduledURL() throws CrawlerException {

		// Retrieve oldest pending URL to crawl
		List<CrawlURL> s = crawlURLRepository.findFirstPendingURLLesserThanNestedLimit(PageRequest.of(0, 1), getNestediterations());

		// If pending URL found
		if (s.size() == 1) {

			CrawlURL entity = s.get(0);
			// Update states to pending so no other schedulers will pick for Crawl

			logger.debug("Fount enttiy for  crawl {} ", entity.getId());
			//No Crawling for max nested iterations 
			//if (entity.getNestediterations()   <= getNestediterations()) {

				entity.setCrawlStatus("P");

				crawlURLRepository.save(entity);
				
				// Extract all anchor tags with Jsoup
				Document doc = null;
				try {
					doc = Jsoup.connect(entity.getCreawleURL()).timeout(10 * 1000).get();
				} catch (IOException e1) {
					// throw new CrawlerException(e1);
				}
				Elements elts = doc.select("a");

				// Update crawl URL object with its body content
				entity.setCrawlStatus("Y");
				entity.setUrlContent(doc.body().text());
				crawlURLRepository.saveAndFlush(entity);
				String actualURL = null;
				int crwlCount = 0;
				for (Element element : elts) {
					crwlCount++;
					// Iteration breaks if max iteration count reached 
					if (crwlCount > maxcrawlcount) {
						break;
					}
					try {
						actualURL = element.attr("href");
						URL url = new URL(actualURL);

						List<CrawlURL> findByCreawleURLIs = crawlURLRepository.findByCreawleURLIs(actualURL);
						if (findByCreawleURLIs.isEmpty()) {
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							connection.setRequestMethod("HEAD");
							connection.connect();
							String contentType = connection.getContentType();
							// Check for content type before shading for nested Crawl
							if (contentType.contains("text/html")) {
								logger.debug("text/html found for => {} ", actualURL);
								// Check for the same URL
								logger.debug("No previous url found for  => {} ", actualURL);
								crawlURLRepository.saveAndFlush(new CrawlURL(0l, entity.getCreawleURL(), actualURL, "",
										"N", Thread.currentThread().getName(), entity.getNestediterations() + 1));

							}

						} else {
							 
						}
					} catch (Throwable e) {
						logger.error("Fount  crawling URL => " + actualURL, e);
					}
				}
			//}
		}

	}

}
