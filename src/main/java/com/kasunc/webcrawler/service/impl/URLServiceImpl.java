package com.kasunc.webcrawler.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${max.crawl.nestediterations}")
	private Integer nestediterations;
	
	
	
	public URLRepository getRepository() {
		return repository;
	}

	public void setRepository(URLRepository repository) {
		this.repository = repository;
	}

	public Integer getNestediterations() {
		return nestediterations;
	}

	public void setNestediterations(Integer nestediterations) {
		this.nestediterations = nestediterations;
	}

	@Override
	public UrlEntity postUrl(UrlEntity urlEntity) throws CrawlerException {
		logger.debug("Start postUrl >   {} ", urlEntity.getUrlString());
		
			
			List<UrlEntity> findByUrlStringIs = repository.findByUrlStringIs(urlEntity.getUrlString());
			if(findByUrlStringIs.isEmpty() ) {
				try {
					return repository.save(urlEntity);
				} catch (Exception e) {
					throw new CrawlerException(String.format("Error postUrl   >  %s ", urlEntity.getUrlString()) ,e)  ;
				}
			}else {
				
				logger.error("Start postUrl URL Already Exsists >   {} ", urlEntity.getUrlString());
				throw new CrawlerException("URL Already Exsists ");
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
