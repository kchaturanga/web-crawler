package com.kasunc.webcrawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kasunc.webcrawler.exception.CrawlerException;
import com.kasunc.webcrawler.model.inout.CrawlInput;
import com.kasunc.webcrawler.model.inout.CrawlOutput;
import com.kasunc.webcrawler.service.CrawlerService;

@Controller
@RequestMapping("/crawl")
public class CrawlController {
	Logger logger = LoggerFactory.getLogger(CrawlController.class);
	@Autowired
	CrawlerService crawlerService;

	@PostMapping
	ResponseEntity<CrawlOutput> crawl(@RequestBody CrawlInput crawlInput) throws CrawlerException {

		logger.debug("Start crawl Endpoint ");

		try {
			crawlerService.crawl(crawlInput);
			logger.debug("Leaving from  crawl Endpoint ");
			return new ResponseEntity<>(new CrawlOutput("OK"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error in  crawl Endpoint ",e);
			return new ResponseEntity<>(new CrawlOutput(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
