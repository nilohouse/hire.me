package br.com.bemobi.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.util.Collections;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.bemobi.service.WallE;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ApiControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	WallE wallE;
	
	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void shouldntAllowInvalidUrl() throws Exception {
		this.mvc.perform(put("/create?url=homerlovesdonut")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldAllowValidUrl() throws Exception {
		this.mvc.perform(put("/create?url=http://homerlovesdonut.org")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnGracefullyForNewAlias() throws Exception {
		String expectedAlias = "LukeIamYourFather";
		String url = "http://reddit.org";
		
		WallEReport response = new WallEReport();
		response.setAlias(expectedAlias);
		response.setDescription(RandomStringUtils.randomAlphabetic(30));
		response.setUrl(url);
		response.setStatistics(Collections.emptyMap());
		
		Mockito.when(wallE.handle(Mockito.any(URL.class), Mockito.anyString())).thenReturn(response);
		
		this.mvc.perform(put("/create?url="+url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.alias", is(expectedAlias)));
	}
}
