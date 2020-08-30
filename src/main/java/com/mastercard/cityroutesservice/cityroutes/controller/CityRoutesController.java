package com.mastercard.cityroutesservice.cityroutes.controller;

import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.cityroutesservice.cityroutes.exception.BadRequestException;
import com.mastercard.cityroutesservice.cityroutes.service.CityRoutesService;

@RestController
public class CityRoutesController {
	private static final Logger logger = LoggerFactory.getLogger(CityRoutesController.class);

	@Autowired
	CityRoutesService cityRoutesService;

	@GetMapping("/citiesConnected")
	public String findCitiesConnectedByOriginDestination(@QueryParam("origin") String origin,
			@QueryParam("destination") String destination) throws BadRequestException {
		logger.info("Start CityRoutesController.findCitiesConnectedByOriginDestination Method with origin city:"
				+ origin + " and destination city:" + destination);
		return cityRoutesService.findCitiesConnectedByOriginDestination(origin, destination);
	}
}