package br.com.bemobi.domain.helper;

import java.net.URL;

import br.com.bemobi.domain.ShortenedURL;

public final class ShortenedURLBuilder {

	private Long id;
	private URL url;

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
	
	public ShortenedURL build() {
		ShortenedURL shortenedURL = new ShortenedURL();
		shortenedURL.setId(id);
		shortenedURL.setUrl(url);
		
		return shortenedURL;
	}
}
