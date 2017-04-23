/**
 * 
 */
package com.wpi.teamd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author blake
 * @version 1.2
 *
 */
public class QueryFactory {
	private static Logger logger = LogManager.getLogger(QueryFactory.class);
	
	/**
	 * Return a query string that can be passed to HTTP URL to request list of airports
	 * 
	 * @param teamName is the name of the team to specify the data copy on server
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getAirportPool(String teamName) {
		return "?team=" + teamName +
				"&action=list&list_type=airports";
	}

	public static String getAirplanePool(String teamName) {
		return "?team=" + teamName +
				"&action=list&list_type=airplanes";
	}

	public static String getFlightPool(String teamName, String airportCode, Date day, Boolean isDeparting) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");

		return "?team=" + teamName +
				"&action=list&list_type=" + (isDeparting ? "departing" : "arriving") +
				"&airport=" + airportCode +
				"&day=" + sdf.format(day); //yyyy_mm_dd //2017-05-05 2017-05-21
	}
	
	/**
	 * Lock the server database so updates can be written
	 * 
	 * @param teamName is the name of the team to acquire the lock
	 * @return the String written to HTTP POST to lock server database 
	 */
	public static String lock (String teamName) {
		return "team=" + teamName + "&action=lockDB";
	}
	
	/**
	 * Unlock the server database after updates are written
	 * 
	 * @param teamName is the name of the team holding the lock
	 * @return the String written to the HTTP POST to unlock server database
	 */
	public static String unlock (String teamName) {
		return "team=" + teamName + "&action=unlockDB";
	}
	

}
