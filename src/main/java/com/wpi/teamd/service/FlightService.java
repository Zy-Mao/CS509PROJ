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
															Date departureDate, Boolean isDeparting,
															Integer seatClass) {
		ArrayList<Flights> flightsList = new ArrayList<>();
		ArrayList<Flight> flightPool = ServerInterface.getFlightPool(departureAirport.code(), departureDate, isDeparting);
		for (Flight flight : flightPool) {
			if (flight.getArrivalAirport().equals(arrivalAirport)
					&& flight.getSeatsInfoByClass(seatClass) > 0) {
				Flights flights = new Flights();
				flights.put(flight, seatClass);
				flightsList.add(flights);
			}
		}
		return flightsList;
	}

	public static ArrayList<Flights> searchFlightsWithOneLeg(Airport departureAirport, Airport arrivalAirport,
															 Date departureDate, Boolean isDeparting,
															 Integer seatClass) {
		ArrayList<Flights> flightsList = new ArrayList<>();
		ArrayList<Flight> firstFlightPool = ServerInterface.getFlightPool(departureAirport.code(), departureDate, isDeparting);
		for (Flight firstFlight : firstFlightPool) {
			if (firstFlight.getSeatsInfoByClass(seatClass) <= 0) {
				continue;
			}
			ArrayList<Flight> secondFlightPool
					= ServerInterface.getFlightPool(firstFlight.getArrivalAirport().code(), departureDate, isDeparting);
			for (Flight secondFlight : secondFlightPool) {
				if (secondFlight.getArrivalAirport().equals(arrivalAirport)
						&& checkFlightsTimeInterval(secondFlight, firstFlight)
						&& firstFlight.getSeatsInfoByClass(seatClass) > 0
						&& secondFlight.getSeatsInfoByClass(seatClass) > 0) {
					Flights flights = new Flights();
					flights.put(firstFlight, seatClass);
					flights.put(secondFlight, seatClass);
					flightsList.add(flights);
				}
			}
		}
		return flightsList;
	}

	private static Boolean checkFlightsTimeInterval(Flight secondFlight, Flight firstFlight) {
		long timeInterval = secondFlight.getDepartTime().getTime() - firstFlight.getArrivalTime().getTime();
		/*Check the time interval, it must between 30 mins and 4 hours.*/
		return (timeInterval / 1000 / 60 >= 30 && timeInterval / 1000 / 60 / 60 <= 4);
	}
}