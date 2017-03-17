package br.com.bemobi.service;

import java.net.URL;

import br.com.bemobi.web.WallEReport;

public interface WallE {

	WallEReport handle(URL url, String customValue);
}
