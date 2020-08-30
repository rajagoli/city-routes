package com.mastercard.cityroutesservice.cityroutes.service;

import com.mastercard.cityroutesservice.cityroutes.exception.BadRequestException;

public interface CityRoutesService {

	public String findCitiesConnectedByOriginDestination(String originCity, String DestinationCity) throws BadRequestException;
}