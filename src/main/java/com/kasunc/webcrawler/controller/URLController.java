package com.kasunc.webcrawler.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kasunc.webcrawler.exception.CrawlerException;
import com.kasunc.webcrawler.model.inout.URLInput;
import com.kasunc.webcrawler.model.inout.URlPostOut;
import com.kasunc.webcrawler.model.jpa.UrlEntity;
import com.kasunc.webcrawler.service.URLService;

@Controller
public class URLController {
	Logger logger = LoggerFactory.getLogger(URLController.class);
	
	@Autowired
	URLService urlService;

	@GetMapping("/url")
	ResponseEntity<List<UrlEntity>> getURLs() throws CrawlerException {
		logger.debug("getURLs requested");
		return new ResponseEntity<>(urlService.getAllURLS(), HttpStatus.OK);

	}

	@PostMapping("/url")
	ResponseEntity<URlPostOut> postURL(@RequestBody URLInput input) {
		logger.debug("start postURL => {} ", input.getUrl());
		try {
			if (urlService.postUrl(new UrlEntity(0l, input.getUrl(), "", "N") ) != null) {
				logger.debug("Retun postURL => {} ",input.getUrl());
				return new ResponseEntity<>(new URlPostOut("200"), HttpStatus.OK);
			} else {
				logger.debug("Error in postURL => {} ", input.getUrl());
				return new ResponseEntity<>(new URlPostOut("Some Error in the saving process "),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.debug("Error in postURL => " ,e);
			return new ResponseEntity<>(new URlPostOut("Some Error in the saving process "),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
