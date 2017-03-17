package br.com.bemobi.service;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

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
	
	private WallE wallE;
	
	@Before
	public void setUp() {
		this.wallE = new WallEImpl(repository, Hashing.murmur3_32());
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
}
