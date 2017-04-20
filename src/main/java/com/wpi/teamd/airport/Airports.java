/**
 * 
 */
package com.wpi.teamd.airport;

import com.wpi.teamd.dao.DaoAirport;
import com.wpi.teamd.dao.ServerInterface;

import java.util.ArrayList;

/**
 * This class aggregates a number of Airport. The aggregate is implemented as an ArrayList.
 * Airports can be added to the aggregate via XML strings in the format returned form the 
 * CS509 server, or as Airport objects using the ArrayList interface. Objects can 
 * be removed from the collection using the ArrayList interface.
 * 
  * @author blake
 *
 */
public class Airports extends ArrayList<Airport> {
//	private static final long serialVersionUID = 1L;
	private static Airports airports = null;
	private Airports() {

	}

	public static Airports getInstance() {
		if (airports == null) {
			airports = new Airports();
			airports = ServerInterface.getAirports();
		}
		return airports;
	}

	public Airport getAirportByCode(String code) {
		for (int i = 0; i < airports.size(); i++) {
			Airport airport = airports.get(i);
			if (airport.code().equals(code)) {
				return airport;
			}
		}
		return null;
	}
}
