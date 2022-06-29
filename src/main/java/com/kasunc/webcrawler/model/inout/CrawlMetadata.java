package com.kasunc.webcrawler.model.inout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CrawlMetadata {

	 private Queue<String> urlQueue;
	 private List<String> visitedURLs;
	 private int breakpoint ; 
	 
	 public CrawlMetadata(int breakpoint) {
		 this.breakpoint = breakpoint ; 
		 urlQueue = new LinkedList<>();
	        visitedURLs = new ArrayList<>();
	}

	public Queue<String> getUrlQueue() {
		return urlQueue;
	}

	public void setUrlQueue(Queue<String> urlQueue) {
		this.urlQueue = urlQueue;
	}

	public List<String> getVisitedURLs() {
		return visitedURLs;
	}

	public void setVisitedURLs(List<String> visitedURLs) {
		this.visitedURLs = visitedURLs;
	}

	public int getBreakpoint() {
		return breakpoint;
	}

	public void setBreakpoint(int breakpoint) {
		this.breakpoint = breakpoint;
	}
	 
	 

}
