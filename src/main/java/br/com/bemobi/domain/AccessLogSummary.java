package br.com.bemobi.domain;


public class AccessLogSummary {
	private String url;
	private Long hits;
	
	public AccessLogSummary(String url, Long hits) {
		this.url = url;
		this.hits = hits;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getHits() {
		return hits;
	}
	
	public void setHits(Long hits) {
		this.hits = hits;
	}
}
