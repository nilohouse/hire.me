package br.com.bemobi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bemobi.domain.AccessLogRepository;
import br.com.bemobi.domain.AccessLogSummary;
import br.com.bemobi.domain.ShortenedURL;
import br.com.bemobi.domain.ShortenedURLRepository;
import br.com.bemobi.web.WallEReport;

import com.google.common.hash.Hashing;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WallEIntegrationTest {
	
	@Autowired
    private TestEntityManager entityManager;

	@Autowired
	private ShortenedURLRepository repository;
	
	@Autowired
	private AccessLogRepository accessLogRepository;
	
	private WallE wallE;
	
	@Before
	public void setUp() {
		this.wallE = new WallEImpl(repository, accessLogRepository, Hashing.murmur3_32());
	}
	
	@After
	public void tearDown() {
		this.wallE = null;
	}

	@Test
	public void handleWithCustomAliasTest() throws MalformedURLException {
		String urlPath = "http://hammer.time";
		URL url = new URL(urlPath);
		String alias = "ucanttouchthis";
		WallEReport result = wallE.handle(url, alias);
		assertNotNull(result);
		assertEquals(alias, result.getAlias());
		assertNull(result.getErrCode());
		assertEquals(urlPath, result.getUrl());
		assertNotNull(result.getStatistics());
		assertNotNull(result.getStatistics().get("time_taken"));
	}

	@Test
	public void handleRepeatedCustomAliasTest() throws MalformedURLException {
		URL url = new URL("http://hammer.time");
		String alias = "ucanttouchthis";
		wallE.handle(url, alias);
		
		WallEReport result = wallE.handle(new URL("http://anotherone.com"), alias);
		assertNotNull(result);
		assertEquals(alias, result.getAlias());
		assertEquals("001", result.getErrCode());
		assertEquals("CUSTOM ALIAS ALREADY EXISTS", result.getDescription());
		assertNull(result.getStatistics());
	}
	
	@Test
	public void handleWithNewAliasTest() throws MalformedURLException {
		String urlPath = "http://hammer.time";
		URL url = new URL(urlPath);
		WallEReport result = wallE.handle(url, null);
		assertNotNull(result);
		assertNotNull(result.getAlias());
		assertNull(result.getErrCode());
		assertEquals(urlPath, result.getUrl());
		assertNotNull(result.getStatistics());
		assertNotNull(result.getStatistics().get("time_taken"));
	}
	
	@Test
	public void saltingTest() throws MalformedURLException {
		String urlPath = "http://hammer.time";
		URL url = new URL(urlPath);
		WallEReport result = wallE.handle(url, null);
		result = wallE.handle(url, null);
		assertNotNull(result);
		assertNotNull(result.getAlias());
		assertNull(result.getErrCode());
		assertEquals(urlPath, result.getUrl());
		assertNotNull(result.getStatistics());
		assertNotNull(result.getStatistics().get("time_taken"));
	}
	
	@Test
	public void createAndRetrieveTest() throws MalformedURLException {
		String urlPath = "http://hammer.time";
		URL url = new URL(urlPath);
		WallEReport result = wallE.handle(url, null);
		ShortenedURL shortenedUrl = wallE.findUrl(result.getAlias());
		assertNotNull(shortenedUrl);
		assertTrue(shortenedUrl.getId() > 0);
		assertEquals(shortenedUrl.getUrl(), urlPath);
	}
	
	@Test
	public void createAndRetrieveWithCustomAliasTest() throws MalformedURLException {
		String urlPath = "http://hammer.time";
		String customAlias = "sayHelloToMyLittleFriend";
		URL url = new URL(urlPath);
		WallEReport result = wallE.handle(url, customAlias);
		ShortenedURL shortenedUrl = wallE.findUrl(result.getAlias());
		assertNotNull(shortenedUrl);
		assertTrue(shortenedUrl.getId() > 0);
		assertEquals(shortenedUrl.getUrl(), urlPath);
		assertEquals(shortenedUrl.getAlias(), customAlias);
	}
	
	@Test
	public void createRetrieveAndCheckAccessLogTest() throws MalformedURLException {
		String urlPath = "http://hammer.time";
		String customAlias = "sayHelloToMyLittleFriend";
		URL url = new URL(urlPath);
		WallEReport result = wallE.handle(url, customAlias);
		wallE.findUrl(result.getAlias());
		
		final List<Map<String, Object>> accessLogSummary = accessLogRepository.getCompleteAccessLogSummary();
		assertNotNull(accessLogSummary);
		assertEquals(1, Long.valueOf(accessLogSummary.get(0).get("hits").toString()).longValue());
	}
	
	@Test
	public void shouldNotExplodeWhenTryingAnEmptySummaryTest() {
		final List<AccessLogSummary> completeSummary = wallE.getCompleteAccessLogSummary();
		assertNotNull(completeSummary);
	}
	
	@Test
	public void createRetrieveAndCheckAccessLogWithMultipleUrlsTest() throws MalformedURLException {
		String urlPath = "http://scarface.com";
		String scarface = "scarface";
		wallE.handle(new URL(urlPath), scarface);
		
		for (int i = 0; i < 10; i++) {
			wallE.findUrl(scarface);
		}
		
		urlPath = "http://godfellas.com";
		String godFellas = "godFellas";
		wallE.handle(new URL(urlPath), godFellas);
		
		for (int i = 0; i < 20; i++) {
			wallE.findUrl(godFellas);
		}
		
		final List<AccessLogSummary> completeSummary = wallE.getCompleteAccessLogSummary();
		assertNotNull(completeSummary);
		assertEquals(10, completeSummary.get(0).getHits().longValue());
		assertEquals(20, completeSummary.get(1).getHits().longValue());
	}
}
