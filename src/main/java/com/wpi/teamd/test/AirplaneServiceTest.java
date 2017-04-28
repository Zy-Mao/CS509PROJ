package com.wpi.teamd.test;

import com.wpi.teamd.entity.Airplane;
import com.wpi.teamd.service.AirplaneService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by mao on 28/04/2017.
 */
public class AirplaneServiceTest {
	@Test
	public void getAirplanePool() throws Exception {
		ArrayList<Airplane> airplanePool = AirplaneService.getAirplanePool();
		assertNotEquals(airplanePool.size(), 0);
	}
}