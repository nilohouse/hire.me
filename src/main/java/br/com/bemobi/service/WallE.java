package br.com.bemobi.service;

import java.net.URL;
import java.util.List;

import br.com.bemobi.domain.AccessLogSummary;
import br.com.bemobi.domain.ShortenedURL;
import br.com.bemobi.web.WallEReport;

public interface WallE {

	WallEReport handle(URL url, String customValue);

	ShortenedURL findUrl(String alias);
	
	List<AccessLogSummary> getCompleteAccessLogSummary();
}
