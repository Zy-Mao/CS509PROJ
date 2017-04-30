package com.wpi.teamd.test;

import com.wpi.teamd.entity.Airplane;
import com.wpi.teamd.service.AirplaneService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

/**
 * Unit test class for AirplaneService class.
 *
 * @author Mao
 * @version 1.0
 * @since 2017-04-28
 *
 *
 *
 */
public class AirplaneServiceTest {
	@Test
	public void getAirplanePool() throws Exception {
		ArrayList<Airplane> airplanePool = AirplaneService.getAirplanePool();
		assertNotEquals(airplanePool.size(), 0);
	}
}