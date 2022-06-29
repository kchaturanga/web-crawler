package com.kasunc.webcrawler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kasunc.webcrawler.model.inout.SearchResponse;
import com.kasunc.webcrawler.repository.CrawlURLRepository;
import com.kasunc.webcrawler.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired 
	private CrawlURLRepository crawlURLRepository ; 
	
	@Override
	public SearchResponse search(String q) {
		
		SearchResponse response = new SearchResponse();
		response.setTerm(q);
		response.setUrls(crawlURLRepository.findByUrlContentContaining(q));
		 
		return response;
	}

}
