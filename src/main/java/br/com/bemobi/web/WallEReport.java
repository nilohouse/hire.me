package br.com.bemobi.web;

import java.util.Map;

public class WallEReport {
	private String alias;
	private String url;
	private String errCode;
	private String description;
	private Map<String, String> statistics;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Map<String, String> getStatistics() {
		return statistics;
	}
	public void setStatistics(Map<String, String> statistics) {
		this.statistics = statistics;
	}
}
