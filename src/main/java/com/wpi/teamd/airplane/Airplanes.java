package com.wpi.teamd.airplane;

import java.util.ArrayList;

/**
 * Created by Mao on 17/3/19.
 */
public class Airplanes extends ArrayList<Airplane> {
    private static Airplanes airplanes = null;
    private Airplanes() {

    }

    public static Airplanes getInstance() {
        if (airplanes == null) {
            airplanes = new Airplanes();
        }

        return airplanes;
    }

    public Airplane getAirplaneByModel(String model) {
        for (int i = 0; i < airplanes.size(); i++) {
            Airplane airplane = airplanes.get(i);
            if (airplane.getModel().equals(model)) {
                return airplane;
            }
        }
        return null;
    }
}
