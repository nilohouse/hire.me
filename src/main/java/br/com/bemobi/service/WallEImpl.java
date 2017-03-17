package br.com.bemobi.service;

import java.net.URL;

import org.springframework.stereotype.Component;

import br.com.bemobi.web.WallEReport;

@Component
public class WallEImpl implements WallE {

	@Override
	public WallEReport handle(URL url, String customValue) {
		return null;
	}

}
