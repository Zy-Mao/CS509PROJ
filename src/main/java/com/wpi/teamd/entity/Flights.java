package com.wpi.teamd.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Mao on 17/3/20.
 */
public class Flights extends LinkedHashMap<Flight, Integer> {

	public ArrayList<Flight> getFlightsList() {
		ArrayList<Flight> flightsList = new ArrayList<>();
		for (Map.Entry<Flight, Integer> entry : this.entrySet()) {
			Flight flight = entry.getKey();
			flightsList.add(flight);
		}
		return flightsList;
	}


	public int getStopTimes() {
		return this.size() - 1;
	}

	public String getDepartureTimeInString() {
		return ((Flight) this.keySet().toArray()[0]).getDepartTimeInString(true);

	}

	public String getArrivalTimeInString() {
		return ((Flight) this.keySet().toArray()[this.size() - 1]).getArrivalTimeInString(true);
	}

	public long getDurationInSecond() {
		return (((Flight) this.keySet().toArray()[this.size() - 1]).getArrivalTime(false).getTime()
				- ((Flight) this.keySet().toArray()[0]).getDepartTime(false).getTime()) / 1000;
	}

	public String getDurationInString() {
		long duration = this.getDurationInSecond();
		return (duration / 3600) + " hour " + ((duration / 60) % 60) + " min";
	}

	public double getTotalPrice() {
		double totalPrice = 0.00;
		for (Map.Entry<Flight, Integer> entry : this.entrySet()) {
			totalPrice += (entry.getValue() == 1) ?
					entry.getKey().getFirstClassPrice() : entry.getKey().getCoachClassPrice();
		}
		return totalPrice;
	}
}
