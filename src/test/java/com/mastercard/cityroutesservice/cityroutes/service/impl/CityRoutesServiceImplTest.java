package com.mastercard.cityroutesservice.cityroutes.service.impl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.mastercard.cityroutesservice.cityroutes.service.CityRoutesService;

@SpringBootTest
@AutoConfigureMockMvc
public class CityRoutesServiceImplTest {

	public final ResultMatcher noMatcher = MockMvcResultMatchers.content().string("no");

	@MockBean
	private CityRoutesService cityRoutesService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void findIfCitiesConnectedWithOutOriginAndDestination() throws Exception {
		String originCity = null;
		String destinationCity = null;
		when(cityRoutesService.findCitiesConnectedByOriginDestination(originCity, destinationCity)).thenReturn("no");
		this.mockMvc.perform(get("/citiesConnected")).andDo(print()).andExpect(status().isOk()).andExpect(noMatcher);
	}
}