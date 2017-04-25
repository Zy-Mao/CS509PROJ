package com.wpi.teamd.entity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Mao on 17/3/20.
 */
public class Flights extends LinkedHashMap<Flight, Integer> {

	public int getStopTimes() {
		return this.size() - 1;
	}

	public Date getDepartureTime() {
		return ((Flight) this.keySet().toArray()[0]).getDepartTime();

	}

	public Date getArrivalTime() {
		return ((Flight) this.keySet().toArray()[this.size() - 1]).getArrivalTime();
	}

	public long getDurationInSecond() {
		return (((Flight) this.keySet().toArray()[this.size() - 1]).getArrivalTime().getTime()
				- ((Flight) this.keySet().toArray()[0]).getDepartTime().getTime()) / 1000;
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
