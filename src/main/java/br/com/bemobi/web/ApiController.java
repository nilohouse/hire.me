package br.com.bemobi.web;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bemobi.domain.ShortenedURL;
import br.com.bemobi.service.WallE;

@RestController
public class ApiController {

	@Autowired
	private WallE wallE;

	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public WallEReport create(
			@RequestParam URL url,
			@RequestParam(name = "CUSTOM_VALUE", required = false) String customValue) {
		return wallE.handle(url, customValue);
	}

	@RequestMapping(value = "/retrieve/{alias}", method = RequestMethod.GET)
	public void retrieve(HttpServletResponse response,
			@PathVariable("alias") String alias) throws IOException {
		final ShortenedURL shortenedURL = wallE.findUrl(alias);
		
		if (shortenedURL != null && shortenedURL.getUrl() != null) {
			response.sendRedirect(shortenedURL.getUrl().toString());
		} else {
			response.sendError(HttpServletResponse.SC_NO_CONTENT,
					"No URL to forward for alias "+alias);
		}
	}
}
