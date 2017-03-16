package br.com.bemobi.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	@RequestMapping(value="/create", method=RequestMethod.PUT)
	public ApiResponse create() {
		return new ApiResponse();
	}
	
	@RequestMapping(value="/retrieve", method=RequestMethod.GET)
	public void retrieve(HttpServletResponse response) throws IOException {
		response.sendRedirect("");
	}
}
