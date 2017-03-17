package br.com.bemobi.service;

import java.net.URL;

import org.springframework.stereotype.Component;

import br.com.bemobi.domain.ShortenedURL;
import br.com.bemobi.web.WallEReport;

@Component
public class WallEImpl implements WallE {

	@Override
	public WallEReport handle(URL url, String customValue) {
		return null;
	}

	@Override
	public ShortenedURL findUrl(String alias) {
		// TODO Auto-generated method stub
		return null;
	}

}
