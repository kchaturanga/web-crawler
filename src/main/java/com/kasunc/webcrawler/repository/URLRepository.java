package com.kasunc.webcrawler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kasunc.webcrawler.model.jpa.UrlEntity;

public interface URLRepository extends JpaRepository<UrlEntity, Long> {
	List<UrlEntity> findByUrlStringIs(String urlString);
	
}
