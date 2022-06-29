package com.kasunc.webcrawler.exception;

public class CrawlerException extends Exception {
	public CrawlerException() { }
	public CrawlerException(String message) {
		super(message);
	}
	public CrawlerException(Exception e) {
		super(e);
	}
	
	public CrawlerException(String message , Exception e) {
		super(message ,  e);
		
	}
}
