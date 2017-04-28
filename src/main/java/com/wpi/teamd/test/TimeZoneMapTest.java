package com.wpi.teamd.test;

import com.wpi.teamd.utils.TimeZoneMap;
import org.junit.Test;

import java.util.Date;

/**
 * Created by mao on 28/04/2017.
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