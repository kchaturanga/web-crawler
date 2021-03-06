package com.kasunc.webcrawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	Logger logger = LoggerFactory.getLogger(MainController.class);

	@GetMapping("")
	String viewHomePage() {
		logger.debug("Index page requested");
		return "index";
	}
}
