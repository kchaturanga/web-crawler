package com.kasunc.webcrawler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.kasunc.webcrawler.model.inout.CrawlInput;
import com.kasunc.webcrawler.model.jpa.CrawlURL;
import com.kasunc.webcrawler.model.jpa.UrlEntity;
import com.kasunc.webcrawler.repository.CrawlURLRepository;
import com.kasunc.webcrawler.repository.URLRepository;
import com.kasunc.webcrawler.service.impl.CrawlerServiceImpl;
import com.kasunc.webcrawler.service.impl.SearchServiceImpl;
import com.kasunc.webcrawler.service.impl.URLServiceImpl;

@ExtendWith(MockitoExtension.class)
 
class WebCrawlerApplicationTests {

	@Mock
	URLRepository repository;

	@Mock
	URLRepository urlRepository;

	@Mock
	CrawlURLRepository crawlURLRepository;
	
	@InjectMocks
	URLServiceImpl urlService;

	@InjectMocks
	CrawlerServiceImpl crawlerService; 
	
	@InjectMocks
	SearchServiceImpl searchService;
	
			
	@Test
	void shouldSaveNewURL() {
		
		UrlEntity entity2 = new UrlEntity(1l, "https://www.testnew.com", "TEST CONTENTnew ", "N");

		lenient().when(repository.findByUrlStringIs(ArgumentMatchers.any(String.class)))
				.thenReturn(Arrays.asList(new UrlEntity[] {}));
		lenient().when(repository.save(ArgumentMatchers.any(UrlEntity.class))).thenReturn(entity2);

		UrlEntity postUrl = null;
		try {
			postUrl = urlService.postUrl(entity2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThat(entity2.getId()).isSameAs(postUrl.getId());

	}

	@Test
	void shouldNotSaveSameURL() {
		UrlEntity entity1 = new UrlEntity(0l, "https://www.test.com", "TEST CONTENT ", "N");
		UrlEntity entity2 = new UrlEntity(1l, "https://www.test.com", "TEST CONTENTnew ", "N");

		lenient().when(repository.findByUrlStringIs(ArgumentMatchers.any(String.class)))
				.thenReturn(Arrays.asList(new UrlEntity[] { entity1 }));
		lenient().when(repository.save(ArgumentMatchers.any(UrlEntity.class))).thenReturn(entity2);

		Exception exception = assertThrows(Exception.class, () -> {
			urlService.postUrl(entity2);
		});
		String expectedMessage = "URL Already Exsists";
		String actualMessage = exception.getMessage();
		System.out.println(actualMessage);
		assertTrue(actualMessage.contains(expectedMessage));

	}
	
	@Test
	void getAllURLS() throws Exception {
		UrlEntity entity1 = new UrlEntity(0l, "https://www.test.com", "TEST CONTENT ", "N");
		UrlEntity entity2 = new UrlEntity(1l, "https://www.test.com", "TEST CONTENTnew ", "N");

		List<UrlEntity> asList = Arrays.asList(new UrlEntity[] { entity1  , entity2});
		
		lenient().when(repository.findAll()) .thenReturn(asList);
		
		List<UrlEntity> result = urlService.getAllURLS();

        assertEquals(asList, result);

	}
	
	@Test
	void schaduleNweURLForCrawl() throws Exception {
		UrlEntity entity1 = new UrlEntity(0l, "https://www.test.com", "TEST CONTENT ", "N");
		CrawlURL crawlUrl = new CrawlURL(0l, entity1.getUrlString(), entity1.getUrlString(), "", "N","TEST THREAD");
		
		CrawlInput in = new CrawlInput(1l); 
 
		lenient().when(urlRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(entity1));
		lenient().when(crawlURLRepository.findByCreawleURLIs( ArgumentMatchers.any(String.class))).thenReturn(Collections.EMPTY_LIST);
		lenient().when(crawlURLRepository.save(ArgumentMatchers.any(CrawlURL.class))).thenReturn(crawlUrl);
		lenient().when(urlRepository.save(ArgumentMatchers.any(UrlEntity.class))).thenReturn(entity1);
		
		
		CrawlURL result = crawlerService.crawl(in);
		assertEquals(result.getId(),crawlUrl.getId());

	}
	
	@Test
	void shouldNotSaveIfUrlIDIsNotInDb() {
		UrlEntity entity1 = new UrlEntity(0l, "https://www.test.com", "TEST CONTENT ", "N");
		CrawlURL crawlUrl = new CrawlURL(0l, entity1.getUrlString(), entity1.getUrlString(), "", "N","TEST THREAD");
		
		CrawlInput in = new CrawlInput(1l); 
 
		lenient().when(urlRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());
		lenient().when(crawlURLRepository.findByCreawleURLIs( ArgumentMatchers.any(String.class))).thenReturn(Collections.EMPTY_LIST);
		lenient().when(crawlURLRepository.save(ArgumentMatchers.any(CrawlURL.class))).thenReturn(crawlUrl);
		lenient().when(urlRepository.save(ArgumentMatchers.any(UrlEntity.class))).thenReturn(entity1);
		
		Exception exception = assertThrows(Exception.class, () -> {
			crawlerService.crawl(in);
		});
		String expectedMessage = "UrlEntity canot be found ";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}
	
	@Test
	void shouldNotSaveIfUrlIDIsAlreadySchaduled() {
		UrlEntity entity1 = new UrlEntity(0l, "https://www.test.com", "TEST CONTENT ", "N");
		CrawlURL crawlUrl = new CrawlURL(0l, entity1.getUrlString(), entity1.getUrlString(), "", "N","TEST THREAD");
		
		CrawlInput in = new CrawlInput(1l); 
 
		lenient().when(urlRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(entity1));
		lenient().when(crawlURLRepository.findByCreawleURLIs( ArgumentMatchers.any(String.class))).thenReturn(Arrays.asList(new CrawlURL[] {crawlUrl} ));
		lenient().when(crawlURLRepository.save(ArgumentMatchers.any(CrawlURL.class))).thenReturn(crawlUrl);
		lenient().when(urlRepository.save(ArgumentMatchers.any(UrlEntity.class))).thenReturn(entity1);
		
		Exception exception = assertThrows(Exception.class, () -> {
			crawlerService.crawl(in);
		});
		String expectedMessage = "URL already queued for crawling ";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}
	
	
	@Test
	void shouldReturnListOfCrawlURLOnSearch() {
		CrawlURL crawlUrl1 = new CrawlURL(0l, "https://www.test.com", "https://www.test.com", "TEST CONTENT 01", "N" ,"TEST THREAD");
		CrawlURL crawlUrl2 = new CrawlURL(0l, "https://www.test.com", "https://www.testtwo.com", "TEST CONTENT 02", "N" ,"TEST THREAD");
		CrawlURL crawlUrl3 = new CrawlURL(0l, "https://www.test.com", "https://www.testtthree.com", "TEST CONTENT 03", "N" ,"TEST THREAD");
		
		List<CrawlURL> asList = Arrays.asList(new CrawlURL[] {crawlUrl1 , crawlUrl2 , crawlUrl3} );
		
		
		lenient().when(crawlURLRepository.findByUrlContentContaining( ArgumentMatchers.any(String.class))).thenReturn(asList);

		  assertEquals(asList, crawlURLRepository.findByUrlContentContaining("TEST"));
	}
	

}
