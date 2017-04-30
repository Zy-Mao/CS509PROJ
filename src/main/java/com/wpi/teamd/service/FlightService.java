package com.wpi.teamd.service;

import com.wpi.teamd.dao.ServerInterface;
import com.wpi.teamd.entity.Airport;
import com.wpi.teamd.entity.Flight;
import com.wpi.teamd.entity.Flights;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * This class provide some service related with flight.
 *
 * @author Mao
 * @version 1.0
 * @since 2017-04-21
 *
 *
 *
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
		
		//Sort option. add by Tang on 2017/4/27
		
		if(sortOption.equals("price")){
			Collections.sort(flightsList, new Comparator<Flights>() {
				@Override public int compare(Flights flight2, Flights flight1){
				 	Double f1_price = flight1.getTotalPrice();
				 	Double f2_price = flight2.getTotalPrice();
				 	return f2_price.compareTo(f1_price);
			 		}
			});
		}
		else if (sortOption.equals("duration")){
			Collections.sort(flightsList, new Comparator<Flights>() {
				@Override public int compare(Flights flight2, Flights flight1){
					Long f1_duration = flight1.getDurationInSecond();
					Long f2_duration = flight2.getDurationInSecond();
					return f2_duration.compareTo(f1_duration);
				 	}
			});
		}
		else{
			Collections.sort(flightsList, new Comparator<Flights>() {
				@Override public int compare(Flights flight2, Flights flight1){
					Integer f1_stops = flight1.getStopTimes();
					Integer f2_stops = flight2.getStopTimes();
					return f2_stops.compareTo(f1_stops);
				 	}
			});
		}
		
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
		ArrayList<Flight> secondFlightPool = ServerInterface.getFlightPool(arrivalAirport.code(), departureDate, false);
		for (Flight firstFlight : firstFlightPool) {
			if (firstFlight.getSeatsInfoByClass(seatClass) <= 0) {
				continue;
			}
			for (Flight secondFlight : secondFlightPool) {
				if (secondFlight.getSeatsInfoByClass(seatClass) <= 0
						|| !checkFlightsTimeInterval(secondFlight, firstFlight)) {
					continue;
				}
				if (secondFlight.getDepartAirport().equals(firstFlight.getArrivalAirport())) {
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
		ArrayList<Flight> thirdFlightPool = ServerInterface.getFlightPool(arrivalAirport.code(), departureDate, false);
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
				for (Flight thirdFlight : thirdFlightPool) {
					if (thirdFlight.getSeatsInfoByClass(seatClass) <= 0
							|| !checkFlightsTimeInterval(thirdFlight, secondFlight)) {
						continue;
					}
					if (thirdFlight.getDepartAirport().equals(secondFlight.getArrivalAirport())) {
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
		long timeInterval = secondFlight.getDepartTime(false).getTime() - firstFlight.getArrivalTime(false).getTime();
		/*Check the time interval, it must between 30 mins and 4 hours.*/
		return (timeInterval / 1000 / 60 >= 30 && timeInterval / 1000 / 60 / 60 <= 4);
	}

	public static Boolean reserveSeat(LinkedHashMap<String, String> flightsOrderList) {
		return ServerInterface.reserveSeat(flightsOrderList);
	}
}