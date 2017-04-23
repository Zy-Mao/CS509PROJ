package com.wpi.teamd.entity;

import com.wpi.teamd.entity.Flight;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Mao on 17/3/20.
 */
public class Flights extends LinkedHashMap<Flight, Integer> {
	public double getTotalPrice() {
		double totalPrice = 0;
		for (Map.Entry<Flight, Integer> entry : this.entrySet()) {
			totalPrice += (entry.getValue() == 1) ?
					entry.getKey().getFirstClassPrice() : entry.getKey().getCoachClassPrice();
		}
		return totalPrice;
	}
}
