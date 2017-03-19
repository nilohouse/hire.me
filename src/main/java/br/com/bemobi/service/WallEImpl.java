package br.com.bemobi.service;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import br.com.bemobi.domain.AccessLog;
import br.com.bemobi.domain.ShortenedURL;
import br.com.bemobi.domain.ShortenedURLRepository;
import br.com.bemobi.domain.AccessLogRepository;
import br.com.bemobi.domain.helper.ShortenedURLBuilder;
import br.com.bemobi.service.helper.WallEReportBuilder;
import br.com.bemobi.web.WallEReport;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashFunction;

@Component
public class WallEImpl implements WallE {

	private ShortenedURLRepository repository;
	
	private AccessLogRepository accessLogRepository;

	private HashFunction hash;

	public WallEImpl(@Autowired ShortenedURLRepository repository,
			@Autowired AccessLogRepository accessLogRepository,
			@Autowired HashFunction hash) {
		this.repository = repository;
		this.accessLogRepository = accessLogRepository;
		this.hash = hash;
	}

	@Override
	public WallEReport handle(URL url, String customValue) {
		long startTime = System.nanoTime();

		if (StringUtils.isBlank(customValue)) {
			customValue = generateAlias(url, startTime);
		}

		ShortenedURL shortUrl = ShortenedURLBuilder.newBuilder().url(url)
				.alias(customValue).build();
		try {

			repository.save(shortUrl);
			long endTime = System.nanoTime();

			final Map<String, String> stats = new ImmutableMap.Builder<String, String>()
					.put("time_taken",
							String.format(
									"%dms",
									TimeUnit.NANOSECONDS.toMillis(endTime
											- startTime))).build();

			return WallEReportBuilder.newBuilder().alias(shortUrl.getAlias())
					.url(shortUrl.getUrl().toString()).stats(stats).build();
		} catch (DataIntegrityViolationException e) {
			return WallEReportBuilder.newBuilder().alias(shortUrl.getAlias())
					.errCode("001").description("CUSTOM ALIAS ALREADY EXISTS")
					.build();
		}
	}

	private String generateAlias(URL url, long salt) {
		return hash
				.newHasher()
				.putLong(salt)
				.putString(url.getProtocol(), Charsets.UTF_8)
				.putString(url.getHost(), Charsets.UTF_8)
				.putString(StringUtils.defaultString(url.getPath()),
						Charsets.UTF_8)
				.putString(StringUtils.defaultString(url.getQuery()),
						Charsets.UTF_8).hash().toString();
	}

	@Override
	public ShortenedURL findUrl(String alias) {
		final ShortenedURL shortenedURL = repository.findByAlias(alias);
		
		if (ShortenedURL.isValid(shortenedURL)) {
			checkIn(shortenedURL);
		}
		
		return shortenedURL;
	}

	private void checkIn(ShortenedURL shortenedURL) {
		AccessLog accessLog = new AccessLog();
		accessLog.setUrl(shortenedURL.getUrl());
		accessLog.setShortenedUrlId(shortenedURL.getId());
		accessLogRepository.save(accessLog);
	}
}
