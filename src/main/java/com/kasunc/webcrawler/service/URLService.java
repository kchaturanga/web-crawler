package com.kasunc.webcrawler.service;

import java.util.List;

import com.kasunc.webcrawler.exception.CrawlerException;
import com.kasunc.webcrawler.model.jpa.UrlEntity;

public interface URLService {
	
	public UrlEntity postUrl(UrlEntity urlEntity) throws CrawlerException;
	public boolean deleteUrl(UrlEntity urlEntity) throws CrawlerException;
	public List<UrlEntity> getAllURLS() throws CrawlerException;
	
	
}
