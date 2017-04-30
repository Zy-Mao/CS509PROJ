package com.wpi.teamd.test;

import com.wpi.teamd.entity.Airport;
import com.wpi.teamd.service.AirportService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

/**
 * Unit test class for AirportService class.
 *
 * @author Mao
 * @version 1.0
 * @since 2017-04-28
 *
 *
 *
 */
public class AirportServiceTest {
	@Test
	public void getAirportPool() throws Exception {
		ArrayList<Airport> airportPool = AirportService.getAirportPool();
		assertNotEquals(airportPool.size(), 0);
	}
}