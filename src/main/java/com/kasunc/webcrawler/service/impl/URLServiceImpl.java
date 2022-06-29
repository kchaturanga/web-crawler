package com.kasunc.webcrawler.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kasunc.webcrawler.exception.CrawlerException;
import com.kasunc.webcrawler.model.jpa.UrlEntity;
import com.kasunc.webcrawler.repository.URLRepository;
import com.kasunc.webcrawler.service.URLService;

@Service
public class URLServiceImpl implements URLService {
	Logger logger = LoggerFactory.getLogger(URLServiceImpl.class);

	@Autowired
	URLRepository repository ; 
	
	@Override
	public UrlEntity postUrl(UrlEntity urlEntity) throws CrawlerException {
		logger.debug("Start postUrl >   {} ", urlEntity.getUrlString());
		try {
			
			if(repository.findByUrlStringIs(urlEntity.getUrlString()).isEmpty() ) {
				return repository.save(urlEntity);
			}else {
				logger.error("Start postUrl URL Already Exsists >   {} ", urlEntity.getUrlString());
				throw new CrawlerException("URL Already Exsists ");
			}
		} catch (Exception e) {
			throw new CrawlerException(String.format("Error postUrl   >  %s ", urlEntity.getUrlString()) ,e)  ;
		}
	}

	@Override
	public boolean deleteUrl(UrlEntity urlEntity) throws CrawlerException {
		try {
			Optional<UrlEntity> entity = repository.findById(urlEntity.getId());
			if(entity.isPresent()) {
				repository.delete(urlEntity);
			}
				
		 
			return true ;
		} catch (Exception e) {
			return false ; 
		}
	}

	@Override
	public List<UrlEntity> getAllURLS() throws   CrawlerException  {
		
		return repository.findAll();
	}

}
