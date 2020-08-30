package com.mastercard.cityroutesservice.cityroutes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CityRoutesControllerTest {
	public final ResultMatcher noMatcher = MockMvcResultMatchers.content().string("no");
	public final ResultMatcher yesMatcher = MockMvcResultMatchers.content().string("yes");

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void checkCitiesConnectedWithOutOriginDestination() throws Exception {
		this.mockMvc.perform(get("/citiesConnected")).andDo(print()).andExpect(status().isOk()).andExpect(noMatcher);
	}

	@Test
	public void checkCitiesConnectedWithOriginWithOutDestination() throws Exception {
		this.mockMvc.perform(get("/citiesConnected?origin=Boston")).andDo(print()).andExpect(status().isOk())
				.andExpect(noMatcher);
	}

	@Test
	public void checkCitiesConnectedWithOutOriginWithDestination() throws Exception {
		this.mockMvc.perform(get("/citiesConnected?origin=&destination=New York")).andDo(print())
				.andExpect(status().isOk()).andExpect(noMatcher);
	}

	@Test
	public void checkCitiesConnectedWithtOriginBostonWithDestinationPhiladelphia() throws Exception {
		this.mockMvc.perform(get("/citiesConnected?origin=Boston&destination=Philadelphia")).andDo(print())
				.andExpect(status().isOk()).andExpect(yesMatcher);
	}

	@Test
	public void checkCitiesConnectedWithtOriginBostonWithDestinationNewark() throws Exception {
		this.mockMvc.perform(get("/citiesConnected?origin=Boston&destination=Newark")).andDo(print())
				.andExpect(status().isOk()).andExpect(yesMatcher);
	}

	@Test
	public void checkCitiesConnectedWithtOriginPhiladelphiaWithDestinationAlbany() throws Exception {
		this.mockMvc.perform(get("/citiesConnected?origin=Philadelphia&destination=Albany")).andDo(print())
				.andExpect(status().isOk()).andExpect(noMatcher);
	}
}