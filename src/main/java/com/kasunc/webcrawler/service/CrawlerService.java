package com.kasunc.webcrawler.service;

import com.kasunc.webcrawler.exception.CrawlerException;
import com.kasunc.webcrawler.model.inout.CrawlInput;
import com.kasunc.webcrawler.model.jpa.CrawlURL;

public interface CrawlerService {
	public CrawlURL crawl(CrawlInput in ) throws CrawlerException;
	public void crawlSchaduledURL() throws CrawlerException ;
	
}
