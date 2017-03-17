package br.com.bemobi.service;

import java.net.URL;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import br.com.bemobi.domain.ShortenedURL;
import br.com.bemobi.domain.ShortenedURLRepository;
import br.com.bemobi.domain.helper.ShortenedURLBuilder;
import br.com.bemobi.service.helper.WallEReportBuilder;
import br.com.bemobi.web.WallEReport;

@Component
public class WallEImpl implements WallE {
	
	private ShortenedURLRepository repository;
	private Map<String, String> stats;

	public WallEImpl(@Autowired ShortenedURLRepository repository) {
		this.repository = repository;
	}

	@Override
	public WallEReport handle(URL url, String customValue) {
		
		if (StringUtils.isBlank(customValue)) {
			customValue = generateAlias(url);
		}
		
		ShortenedURL shortUrl = ShortenedURLBuilder.newBuilder()
				.url(url)
				.alias(customValue)
				.build();
		
		try {
			repository.save(shortUrl);
			
			return WallEReportBuilder.newBuilder()
					.alias(shortUrl.getAlias())
					.url(shortUrl.getUrl().toString())
					.stats(getStats())
					.build();
		} catch (DataIntegrityViolationException e) {
			return WallEReportBuilder.newBuilder()
					.alias(shortUrl.getAlias())
					.errCode("001")
					.description("CUSTOM ALIAS ALREADY EXISTS")
					.build();
		}
	}

	private String generateAlias(URL url) {
		this.stats = Maps.newHashMap();
		return null;
	}

	private Map<String, String> getStats() {
		return this.stats;
	}

	@Override
	public ShortenedURL findUrl(String alias) {
		// TODO Auto-generated method stub
		return null;
	}
}
