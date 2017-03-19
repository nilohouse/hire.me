package br.com.bemobi.web;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bemobi.domain.AccessLogSummary;
import br.com.bemobi.domain.ShortenedURL;
import br.com.bemobi.service.WallE;
import br.com.bemobi.service.helper.WallEReportBuilder;

@RestController
public class ApiController {
	
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private WallE wallE;

	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public WallEReport create(
			@RequestParam URL url,
			@RequestParam(name = "CUSTOM_ALIAS", required = false) String customAlias) {
		return wallE.handle(url, customAlias);
	}

	@RequestMapping(value = "/retrieve/{alias}", method = RequestMethod.GET)
	public WallEReport retrieve(HttpServletResponse response,
			@PathVariable String alias) throws IOException {
		final ShortenedURL shortenedURL = wallE.findUrl(alias);
		
		if (ShortenedURL.isValid(shortenedURL)) {
			log.info("Redirecting alias {} to URL {}", alias, shortenedURL.getUrl().toString());
			response.sendRedirect(shortenedURL.getUrl().toString());
			return WallEReportBuilder.emptyReport();
		} else {
			log.warn("Alias not found: {}", alias);
			return WallEReportBuilder.newBuilder()
					.errCode("002")
					.description("SHORTENED URL NOT FOUND")
					.build();
		}
	}
	
	@RequestMapping(value = "/top10", method = RequestMethod.GET)
	public List<AccessLogSummary> top10() {
		return wallE.getCompleteAccessLogSummary();
	}
}
