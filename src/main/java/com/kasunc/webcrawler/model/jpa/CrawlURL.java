package com.kasunc.webcrawler.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "crwlurls")
public class CrawlURL {
     
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    @Column(name = "rooturl"  , length = 255)
    private String rootURL;
     
    @Column(name = "crwlurl"  , length = 255)
    private String creawleURL;
     
    @Column(name = "urlcontent" , columnDefinition="CHARACTER LARGE OBJECT")
    private String urlContent;
    
    
    @Column(name = "crwlStatus" )
    private String crawlStatus;
    
    @Column(name = "worker" )
    private String worker;
    
    @Column(name = "nestediterations" )
    private int nestediterations;
    
    public CrawlURL() { }
   

	public CrawlURL(Long id, String rootURL, String creawleURL, String urlContent, String crawlStatus, String worker ) {
		super();
		this.id = id;
		this.rootURL = rootURL;
		this.creawleURL = creawleURL;
		this.urlContent = urlContent;
		this.crawlStatus = crawlStatus;
		this.worker = worker ; 
		this.nestediterations = 1 ;
	}
	public CrawlURL(Long id, String rootURL, String creawleURL, String urlContent, String crawlStatus, String worker , int nestediterations) {
		super();
		this.id = id;
		this.rootURL = rootURL;
		this.creawleURL = creawleURL;
		this.urlContent = urlContent;
		this.crawlStatus = crawlStatus;
		this.worker = worker ; 
		this.nestediterations = nestediterations;
	}



	public String getWorker() {
		return worker;
	}


	public void setWorker(String worker) {
		this.worker = worker;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRootURL() {
		return rootURL;
	}

	public void setRootURL(String rootURL) {
		this.rootURL = rootURL;
	}

	public String getCreawleURL() {
		return creawleURL;
	}

	public void setCreawleURL(String creawleURL) {
		this.creawleURL = creawleURL;
	}

	public String getUrlContent() {
		return urlContent;
	}

	public void setUrlContent(String urlContent) {
		this.urlContent = urlContent;
	}

	public String getCrawlStatus() {
		return crawlStatus;
	}

	public void setCrawlStatus(String crawlStatus) {
		this.crawlStatus = crawlStatus;
	}


	public int getNestediterations() {
		return nestediterations;
	}


	public void setNestediterations(int nestediterations) {
		this.nestediterations = nestediterations;
	}
 
     
    
     
   
}