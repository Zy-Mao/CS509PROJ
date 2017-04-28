package com.wpi.teamd.test;

import com.wpi.teamd.entity.Airport;
import com.wpi.teamd.service.AirportService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by mao on 28/04/2017.
 */
public class AirportServiceTest {
	@Test
	public void getAirportPool() throws Exception {
		ArrayList<Airport> airportPool = AirportService.getAirportPool();
		assertNotEquals(airportPool.size(), 0);
	}
}