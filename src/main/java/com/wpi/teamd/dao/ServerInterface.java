/**
 * 
 */
package com.wpi.teamd.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.wpi.teamd.airplane.Airplanes;
import com.wpi.teamd.airport.Airports;
import com.wpi.teamd.flight.Flights;
import com.wpi.teamd.utils.QueryFactory;

/**
 * This class provides an interface to the CS509 server. It provides sample methods to perform
 * HTTP GET and HTTP POSTS
 *   
 * @author blake
 * @version 1.1
 * @since 2016-02-24
 *
 */
public class ServerInterface {
	private static final String mUrlBase = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";
	private static final String teamName = "TeamD";
	/**
	 * Return a collection of all the airports from server
	 * 
	 * Retrieve the list of airports available to the specified ticketAgency via HTTPGet of the server
	 *
	 * @return collection of Airports from server
	 */
	public static Airports getAirports () {
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();

		String xmlAirports;
		Airports airports;

		try {
			/**
			 * Create an HTTP connection to the server for a GET 
			 */
			url = new URL(mUrlBase + QueryFactory.getAirports(teamName));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", teamName);

			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		xmlAirports = result.toString();
		airports = DaoAirport.addAll(xmlAirports);
		return airports;
		
	}

	public static Airplanes getAirplanes () {
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();

		String xmlAirplanes;
		Airplanes airplanes;

		try {
			/**
			 * Create an HTTP connection to the server for a GET
			 */
			url = new URL(mUrlBase + QueryFactory.getAirplanes(teamName));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", teamName);

			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		xmlAirplanes = result.toString();
		airplanes = DaoAirplane.addAll(xmlAirplanes);
		return airplanes;
	}

	public static Flights getFlights(String airportCode, Date day, Boolean isDeparting) {
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();

		String xmlFlights;
		Flights flights;

		try {
			/**
			 * Create an HTTP connection to the server for a GET
			 */
//			url = new URL(mUrlBase + QueryFactory.getAirplanes(teamName));
			url = new URL(mUrlBase + QueryFactory.getFlights(teamName, airportCode, day, isDeparting));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", teamName);

			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		xmlFlights = result.toString();
		flights = DaoFlight.addAll(xmlFlights);
		return flights;
	}

	/**
	 * Lock the database for updating by the specified team. The operation will fail if the lock is held by another team.
	 *
	 * @return true if the server was locked successfully, else false
	 */
	public static boolean lock () {
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(mUrlBase);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", teamName);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String params = QueryFactory.lock(teamName);
			
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
			
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to lock database");
			System.out.println(("\nResponse Code : " + responseCode));
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			
			System.out.println(response.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Unlock the database previous locked by specified team. The operation will succeed if the server lock is held by the specified
	 * team or if the server is not currently locked. If the lock is held be another team, the operation will fail.
	 * 
	 * The server interface to unlock the server interface uses HTTP POST protocol
	 *
	 * @return true if the server was successfully unlocked.
	 */
	public boolean unlock () {
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(mUrlBase);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			
			String params = QueryFactory.unlock(teamName);
			
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
		    
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to unlock database");
			System.out.println(("\nResponse Code : " + responseCode));

			if (responseCode >= HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response = new StringBuffer();

				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				System.out.println(response.toString());
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
}
