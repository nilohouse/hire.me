package br.com.bemobi.domain.helper;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bemobi.domain.ShortenedURL;

public final class ShortenedURLBuilder {

	private static final Logger log = LoggerFactory.getLogger(ShortenedURLBuilder.class);
	
	private Long id;
	private URL url;
	private String alias;

	private ShortenedURLBuilder() {
		//
	}
	
	public static ShortenedURLBuilder newBuilder() {
		return new ShortenedURLBuilder();
	}
	
	public ShortenedURLBuilder id(Long id) {
		this.id = id;
		return this;
	}
	
	public ShortenedURLBuilder url(URL url) {
		this.url = url;
		return this;
	}
	
	public ShortenedURLBuilder url(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			log.warn("Invalid url: {}", url);
		}
		return this;
	}
	
	public ShortenedURLBuilder alias(String alias) {
		this.alias = alias;
		return this;
	}
	
	public ShortenedURL build() {
		ShortenedURL shortenedURL = new ShortenedURL();
		shortenedURL.setId(id);
		shortenedURL.setUrl(url.toString());
		shortenedURL.setAlias(alias);
		
		return shortenedURL;
	}
}
