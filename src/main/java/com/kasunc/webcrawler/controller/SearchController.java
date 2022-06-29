package com.kasunc.webcrawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kasunc.webcrawler.model.inout.SearchResponse;
import com.kasunc.webcrawler.service.SearchService;

@Controller
@RequestMapping("/search")
public class SearchController {
	Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	SearchService searchService;

	@GetMapping("/view")
	public String viewHomePage() {
		logger.debug("Search home page requested");
		return "search";
	}

	@GetMapping("/results")
	public ResponseEntity<SearchResponse> getResults(@RequestParam String q) {
		logger.debug("Search results requested for term  {}" , q);
		try {
			SearchResponse search = searchService.search(q);
			return new ResponseEntity<>(search, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error in results   ", e);
			return new ResponseEntity<>(new SearchResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
