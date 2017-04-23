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

	public static ArrayList<Flights> searchFlights(Integer seatClass,
												   Airport departureAirport, Airport arrivalAirport,
												   Date departureDate,
												   Integer stopOverTimes, String sortOption
												   ) {
		ArrayList<Flights> flightsList = new ArrayList<>();
		switch (stopOverTimes) {
			case 2:
				flightsList.addAll(searchFlightsWithTwoStop(seatClass, departureAirport, arrivalAirport, departureDate));
			case 1:
				flightsList.addAll(searchFlightsWithOneStop(seatClass, departureAirport, arrivalAirport, departureDate));
			case 0:
			default:
				flightsList.addAll(searchFlightsWithNoStop(seatClass, departureAirport, arrivalAirport, departureDate));
		}
		//TODO sort option
		return flightsList;
	}

	private static ArrayList<Flights> searchFlightsWithNoStop(Integer seatClass,
															Airport departureAirport, Airport arrivalAirport,
															Date departureDate) {
		ArrayList<Flights> flightsList = new ArrayList<>();
		ArrayList<Flight> flightPool = ServerInterface.getFlightPool(departureAirport.code(), departureDate, true);
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

	private static ArrayList<Flights> searchFlightsWithOneStop(Integer seatClass,
															 Airport departureAirport, Airport arrivalAirport,
															 Date departureDate) {
		ArrayList<Flights> flightsList = new ArrayList<>();
		ArrayList<Flight> firstFlightPool = ServerInterface.getFlightPool(departureAirport.code(), departureDate, true);
		for (Flight firstFlight : firstFlightPool) {
			if (firstFlight.getSeatsInfoByClass(seatClass) <= 0) {
				continue;
			}
			ArrayList<Flight> secondFlightPool
					= ServerInterface.getFlightPool(firstFlight.getArrivalAirport().code(), departureDate, true);
			for (Flight secondFlight : secondFlightPool) {
				if (secondFlight.getSeatsInfoByClass(seatClass) <= 0
						|| !checkFlightsTimeInterval(secondFlight, firstFlight)) {
					continue;
				}
				if (secondFlight.getArrivalAirport().equals(arrivalAirport)) {
					Flights flights = new Flights();
					flights.put(firstFlight, seatClass);
					flights.put(secondFlight, seatClass);
					flightsList.add(flights);
				}
			}
		}
		return flightsList;
	}

	private static ArrayList<Flights> searchFlightsWithTwoStop(Integer seatClass,
															 Airport departureAirport, Airport arrivalAirport,
															 Date departureDate) {
		ArrayList<Flights> flightsList = new ArrayList<>();
		ArrayList<Flight> firstFlightPool = ServerInterface.getFlightPool(departureAirport.code(), departureDate, true);
		for (Flight firstFlight : firstFlightPool) {
			if (firstFlight.getSeatsInfoByClass(seatClass) <= 0) {
				continue;
			}
			ArrayList<Flight> secondFlightPool
					= ServerInterface.getFlightPool(firstFlight.getArrivalAirport().code(), departureDate, true);
			for (Flight secondFlight : secondFlightPool) {
				if (secondFlight.getSeatsInfoByClass(seatClass) <= 0
						|| !checkFlightsTimeInterval(secondFlight, firstFlight)) {
					continue;
				}
				ArrayList<Flight> thirdFlightPool
						= ServerInterface.getFlightPool(secondFlight.getArrivalAirport().code(), departureDate, true);
				for (Flight thirdFlight : thirdFlightPool) {
					if (thirdFlight.getSeatsInfoByClass(seatClass) <= 0
							|| !checkFlightsTimeInterval(thirdFlight, secondFlight)) {
						continue;
					}
					if (thirdFlight.getArrivalAirport().equals(arrivalAirport)) {
						Flights flights = new Flights();
						flights.put(firstFlight, seatClass);
						flights.put(secondFlight, seatClass);
						flights.put(thirdFlight, seatClass);
						flightsList.add(flights);
				}
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