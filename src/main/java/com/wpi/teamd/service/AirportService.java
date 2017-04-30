package com.wpi.teamd.service;

import com.wpi.teamd.dao.ServerInterface;
import com.wpi.teamd.entity.Airport;

import java.util.ArrayList;

/**
 * This class provide some service related with airport.
 *
 * @author Mao
 * @version 1.0
 * @since 2017-04-21
 *
 *
 *
 */
public class AirportService {
	private static ArrayList<Airport> airportPool = null;
	private AirportService() {

	}

	public static ArrayList<Airport> getAirportPool() {
		if (airportPool == null) {
			airportPool = new ArrayList<>();
			airportPool = ServerInterface.getAirportPool();
		}
		return airportPool;
	}

	public static Airport getAirportByCode(String code) {
		for (Airport airport : AirportService.getAirportPool()) {
			if (airport.code().equals(code)) {
				return airport;
			}
		}
		return null;
	}
}
