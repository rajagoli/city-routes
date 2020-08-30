package com.mastercard.cityroutesservice.cityroutes.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.mastercard.cityroutesservice.cityroutes.exception.BadRequestException;
import com.mastercard.cityroutesservice.cityroutes.service.CityRoutesService;

@Service
public class CityRoutesServiceImpl implements CityRoutesService {
	private static final Logger logger = LoggerFactory.getLogger(CityRoutesServiceImpl.class);
	private Map<String, String> inputCityPairs = new HashMap<String, String>();
	private Map<String, LinkedHashSet<String>> inputCityRoads = new HashMap<String, LinkedHashSet<String>>();

	@Override
	public String findCitiesConnectedByOriginDestination(String originCity, String destinationCity)
			throws BadRequestException {
		logger.info("Start CityRoutesServiceImpl.findCitiesConnectedByOriginDestination()...with origin city:"
				+ originCity + " and destination city:" + destinationCity);
		if (originCity == null || "".equals(originCity) || destinationCity == null || "".equals(destinationCity)) {
			logger.error(
					"Start CityRoutesServiceImpl.findCitiesConnectedByOriginDestination()... originCity or destinationCity is null or empty");
			throw new BadRequestException("no");
		}
		if (isCitiesConnectedDirectly(originCity, destinationCity)
				|| isCitiesConnectedInDirectly(originCity, destinationCity)) {
			return "yes";
		} else {
			return "no";
		}
	}

	@PostConstruct
	private void buildCityPairsAndRoads() throws IOException {
		logger.info("Start CityRoutesServiceImpl.init()...");
		InputStream resource = new ClassPathResource("cities.txt").getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
		inputCityPairs = reader.lines().map(line -> line.split(","))
				.collect(Collectors.toMap(line -> line[0], line -> line[1].trim()));

		inputCityPairs.forEach((originCity, destinationCity) -> {
			if (inputCityRoads.containsKey(originCity)) {
				inputCityRoads.get(originCity).add(destinationCity);
			} else {
				LinkedHashSet<String> destinationCityHashSet = new LinkedHashSet<String>();
				destinationCityHashSet.add(destinationCity);
				inputCityRoads.put(originCity, destinationCityHashSet);
			}

			if (inputCityRoads.containsKey(destinationCity)) {
				inputCityRoads.get(destinationCity).add(originCity);
			} else {
				LinkedHashSet<String> originCityHashSet = new LinkedHashSet<String>();
				originCityHashSet.add(originCity);
				inputCityRoads.put(destinationCity, originCityHashSet);
			}

		});
		logger.info("End CityRoutesServiceImpl.init()...");
	}

	private boolean isCitiesConnectedDirectly(String originCity, String destinationCity) {
		logger.info("Start CityRoutesServiceImpl.isCitiesConnectedDirectly()...");
		if ((inputCityRoads.get(originCity) != null && inputCityRoads.get(originCity).contains(destinationCity))) {
			logger.info("End CityRoutesServiceImpl.isCitiesConnectedDirectly()...");
			return true;
		}
		logger.info("End CityRoutesServiceImpl.isCitiesConnectedDirectly()...");
		return false;
	}

	private boolean isCitiesConnectedInDirectly(String originCity, String destinationCity) {
		logger.info("Start CityRoutesServiceImpl.isCitiesConnectedInDirectly()...");
		LinkedHashSet<String> citiesVerified = new LinkedHashSet<String>();
		Queue<String> citiesToBeVerified = new ConcurrentLinkedQueue<String>();
		citiesToBeVerified.add(originCity);
		while (!citiesToBeVerified.isEmpty()) {
			String currentCity = citiesToBeVerified.poll();
			if (isCitiesConnectedDirectly(currentCity, destinationCity)) {
				return true;
			} else {
				citiesVerified.add(currentCity);
				for (String nextCityToBeVerified : inputCityRoads.get(currentCity)) {
					if (!citiesVerified.contains(nextCityToBeVerified)) {
						citiesToBeVerified.add(nextCityToBeVerified);
					}
				}
			}
		}

		logger.info("End CityRoutesServiceImpl.isCitiesConnectedInDirectly()...");
		return false;
	}
}