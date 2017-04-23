package com.wpi.teamd.service;

import com.wpi.teamd.dao.ServerInterface;
import com.wpi.teamd.entity.Airplane;

import java.util.ArrayList;

/**
 * Created by mao on 2017/4/21.
 */
public class AirplaneService {
	private static ArrayList<Airplane> airplanePool = null;
	private AirplaneService() {

	}

	public static ArrayList<Airplane> getAirplanePool() {
		if (airplanePool == null) {
			airplanePool = new ArrayList<>();
			airplanePool = ServerInterface.getAirplanePool();
		}
		return airplanePool;
	}

	public static Airplane getAirplaneByModel(String model) {

		for (Airplane airplane : AirplaneService.getAirplanePool()) {
			if (airplane.getModel().equals(model)) {
				return airplane;
			}
		}
		return null;
	}
}
