package com.kasunc.webcrawler.model.inout;

import java.util.List;

import com.kasunc.webcrawler.model.jpa.CrawlURL;

public class SearchResponse {
	
	private List< CrawlURL > urls ;
	private String term ;
	private int itemsPerPage ;
	private int pageNumber;
	private int total ;
	
	public SearchResponse() { }
	public SearchResponse(List<CrawlURL> urls, String term) {
		super();
		this.urls = urls;
		this.term = term;
	}
	public List<CrawlURL> getUrls() {
		return urls;
	}
	public void setUrls(List<CrawlURL> urls) {
		this.urls = urls;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	} 
	
	
	
	
	
}
