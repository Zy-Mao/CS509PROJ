/**
 * 
 */
package driver;

import airplane.Airplanes;
import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import view.FlightSearchFrame;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author blake
 *
 */
public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Airports airports = ServerInterface.getAirports();
		Airplanes airplanes = ServerInterface.getAirplanes();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//			Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
		}

		new FlightSearchFrame(200, 200);

		for (int i = 0; i < airports.size(); i++) {
			System.out.println(airports.get(i).toString());
		}

		for (int i = 0; i < airplanes.size(); i++) {
			System.out.println(airplanes.get(i).toString());
		}

//		if (args.length != 1) {
//			System.err.println("usage: CS509.sample teamName");
//			System.exit(-1);
//			return;
//		}

//		for (Airport airport : airports) {
//			System.out.println(airport.toString());
//		}
	}
}
