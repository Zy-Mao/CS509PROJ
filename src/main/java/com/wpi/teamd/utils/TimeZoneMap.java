package com.wpi.teamd.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class holds values pertaining to a single Airport. Class member attributes
 * are the same as defined by the CS509 server API and store values after conversion from
 * XML received from the server to Java primitives. Attributes are accessed via getter and
 * setter methods.
 *
 * @author Zheng
 * @version 1.0
 * @since 2017-04-27
 *
 *
 *
 */

public class TimeZoneMap {

	public static String timeZoneId(double latitude, double longitude) {

		if (longitude >= -67.47 && longitude < -52.31) {
			return "America/Halifax";
		} else if (longitude >= -84.52) {
			return "America/New_York";
		} else if (longitude >= -87.28) {
			if (latitude > 36.33) {
				return "America/New_York";
			} else {
				return "America/Chicago";
			}
		} else if (longitude >= -100.92) {
			return "America/Chicago";
		} else if (longitude >= -103.26) {
			if (latitude > 46.05) {
				return "America/Chicago";
			} else if (latitude >= 35.32) {
				return "America/Denver";
			} else {
				return "America/Chicago";
			}
		} else if (longitude >= -109.02) {
			if (latitude >= 47.22) {
				return "America/Chicago";
			} else if (latitude > 30.00) {
				return "America/Denver";
			} else {
				return "America/Chicago";
			}
		} else if (longitude >= -113.16) {
			return "America/Denver";
		} else if (longitude >= -116.16) {
			if (latitude > 44.21) {
				return "America/Los_Angeles";
			} else if (latitude > 41.05) {
				return "America/Denver";
			} else {
				return "America/Los_Angeles";
			}
		} else if (longitude > -130.31) {
			return "America/Los_Angeles";
		} else if (longitude > -179.12) {
			if (longitude >= 26.47) {
				return "America/Anchorage";
			} else {
				return "Pacific/Honolulu";
			}
		} else {
			return "Europe/London";
		}
	}

	public static String getFormattedTime(Date date, double latitude, double longitude, boolean isLocal) {
		DateFormat formatter = new SimpleDateFormat("HH:mm z");
		if (isLocal) {
			formatter.setTimeZone(TimeZone.getTimeZone(timeZoneId(latitude, longitude)));
		}
		return formatter.format(date);
	}

	public static String localGmt(Date date) {
		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return formatter.format(date);
	}
}
