package br.com.bemobi.service.helper;

import java.util.Map;

import br.com.bemobi.web.WallEReport;

public final class WallEReportBuilder {

	private String alias;
	private String url;
	private String description;
	private String errCode;
	private Map<String, String> stats;

	private WallEReportBuilder() {
		//
	}
	
	public static WallEReportBuilder newBuilder() {
		return new WallEReportBuilder();
	}

	public WallEReportBuilder alias(String alias) {
		this.alias = alias;
		return this;
	}
	
	public WallEReportBuilder url(String url) {
		this.url = url;
		return this;
	}
	
	public WallEReportBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public WallEReportBuilder errCode(String errCode) {
		this.errCode = errCode;
		return this;
	}
	
	public WallEReportBuilder stats(Map<String, String> stats) {
		this.stats = stats;
		return this;
	}
	
	public WallEReport build() {
		WallEReport report = new WallEReport();
		report.setAlias(alias);
		report.setUrl(url);
		report.setDescription(description);
		report.setErrCode(errCode);
		report.setStatistics(stats);
		return report;
	}
}
