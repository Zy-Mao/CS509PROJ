package com.wpi.teamd.service;

import com.wpi.teamd.dao.DaoFlight;
import com.wpi.teamd.dao.ServerInterface;
import com.wpi.teamd.entity.Airport;
import com.wpi.teamd.entity.Flight;
import com.wpi.teamd.entity.Flights;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mao on 2017/4/21.
 */
public class FlightService {
	private static Logger logger = LogManager.getLogger(FlightService.class);
	private FlightService() {

	}

	public static ArrayList<Flights> searchFlightsWithNoLeg(Airport departureAirport, Airport arrivalAirport,
															Date departureDate, Boolean isDeparting) {
		ArrayList<Flights> flightsList = new ArrayList<>();
		ArrayList<Flight> flightPool = ServerInterface.getFlightPool(departureAirport.code(), departureDate, isDeparting);
		logger.debug(flightPool.size());
		for (Flight flight : flightPool) {
			logger.debug(flight.getDepartAirport().code() + flight.getArrivalAirport().code());
			if (flight.getArrivalAirport().equals(arrivalAirport)) {
				Flights flights = new Flights();
				flights.add(flight);
				flightsList.add(flights);
			}
		}
		return flightsList;
	}

}
