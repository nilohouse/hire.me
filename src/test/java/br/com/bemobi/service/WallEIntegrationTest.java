package br.com.bemobi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import br.com.bemobi.domain.ShortenedURLRepository;
import br.com.bemobi.web.WallEReport;

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
		this.wallE = new WallEImpl(repository);
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
	}
}
