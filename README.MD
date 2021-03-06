# Web Crawler

web crawler that can ingest a list of URLs and do a search for text 

## Installation

Either use docker or direcly run as a spring boot aplication 

### Docker
```bash
sudo ./gradlew bootBuildImage --imageName=kasunc/web-crawler
sudo docker run -p 8080:8080 kasunc/web-crawler --server.port=8080
```
### Spring Boot
```bash
./gradlew bootrun
```

## Usage
### Adding URLS 
Access http://localhost:8080/ 

Add your URL in to input box , You can add many URLs as possible 

Start crawling by clicking "Crawl URL " Button 

This will add a backed record for crawl pending  and Crawlers ( Spring Scheduler runs every half a second with 10 parallel threads are configured as crawlers ) will pick that URL and start crawling . 

Worker will take 
* crawl waiting record ( Status N and Lesser than max Nested Crawls ) from database
* fetch the HTML DOMs anchor tag list using jsoup 
* While traversing save each valid URLS as new pending records 
* Set nested crawl count + one from parent record 
* Save content of the document body in the parent record 
* Workers will run 4 nested URLs and 100 MAX in each iteration 

* CrawlerServiceImpl.java / scheduleFixedDelayTask()
```java
	@Async
	@Scheduled(fixedDelay = 500)
	@Override
	public void crawlSchaduledURL() throws CrawlerException { }
```
### Search 
Access http://localhost:8080/search/view for search window 
Type your query and click search 

## Limitations and Improvement
### Limitations 
* At the moment URL crawlers will run 4 nested URLs and 100 MAX in each iteration  to crawl nested URLS  
* At the moment application uses in memory H2 Database and that need to change 
* No pagination for results and crawl display 
* At the moment , in search full text fetched by URL comes to front end , Which can cause memory issues in browser . 
* No Expiration logc
* Crawling logics need a improvement
* Configure log paths in logback.xml before start the appliaction  
### Improvements 
* For search options , we can utilize hibernate full text search for indexing with elastic search 
* Need to style / improve basic UI or add a different front end by making this as backend api  

