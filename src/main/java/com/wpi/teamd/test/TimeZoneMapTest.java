package com.wpi.teamd.test;

import com.wpi.teamd.utils.TimeZoneMap;
import org.junit.Test;

import java.util.Date;

/**
 * Unit test class for TimeZoneMapF class.
 *
 * @author Mao
 * @version 1.0
 * @since 2017-04-28
 *
 *
 *
 */
public class TimeZoneMapTest {
	@Test
	public void getFormattedTime() throws Exception {
		Date date = new Date(30, 0, 0, 0, 0, 0);
		String formattedTime = TimeZoneMap.getFormattedTime(date, 47, -105, true);
		System.out.println(formattedTime);
		assert (formattedTime.equals("22:00 MST"));
	}

}