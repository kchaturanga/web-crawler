package com.kasunc.webcrawler.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "urls")
public class UrlEntity {
     
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    @Column(name = "urlstr" ,nullable = false, unique = true, length = 45)
    private String urlString;
     
    @Column(name = "crwcomment" , nullable = false, length = 64)
    private String creawledComments;
     
    @Column(name = "crwstatus", nullable = false, length = 20)
    private String crawlledStatus;
    
    public UrlEntity() { }

	public UrlEntity(Long id, String urlString, String creawledComments, String crawlledStatus) {
		super();
		this.id = id;
		this.urlString = urlString;
		this.creawledComments = creawledComments;
		this.crawlledStatus = crawlledStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public String getCreawledComments() {
		return creawledComments;
	}

	public void setCreawledComments(String creawledComments) {
		this.creawledComments = creawledComments;
	}

	public String getCrawlledStatus() {
		return crawlledStatus;
	}

	public void setCrawlledStatus(String crawlledStatus) {
		this.crawlledStatus = crawlledStatus;
	}
     
    
     
   
}