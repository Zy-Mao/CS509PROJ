package com.wpi.teamd.servlet;

import com.wpi.teamd.entity.Airport;
import com.wpi.teamd.entity.Flights;
import com.wpi.teamd.service.AirportService;
import com.wpi.teamd.service.FlightService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mao on 2017/4/20.
 */
@WebServlet(name = "SearchServlet", urlPatterns={"/search"})
public class SearchServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(SearchServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean roundTrip = request.getParameter("trip-route") != null
				&& request.getParameter("trip-route").equals("round-trip");
		Integer seatClass = request.getParameter("seat-class") != null
				&& request.getParameter("seat-class").equals("first-class") ? 1 : 2;
		String departureAirportCode = request.getParameter("departure-airport");
		String arrivalAirportCode = request.getParameter("arrival-airport");
		String departureDateString = request.getParameter("departure-date");
		String returnDateString = request.getParameter("return-date");
		Integer stopOverTimes = request.getParameter("stop-over-times") != null ?
				Integer.parseInt(request.getParameter("stop-over-times")) : 0;
		String sortOption = request.getParameter("sort-option");
		if (sortOption == null) sortOption = "price";

		Airport departureAirport = AirportService.getAirportByCode(departureAirportCode);
		Airport arrivalAirport = AirportService.getAirportByCode(arrivalAirportCode);

		Date departureDate = null;
		Date returnDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		try {
			departureDate = simpleDateFormat.parse(departureDateString);
			returnDate = returnDateString != null ? simpleDateFormat.parse(returnDateString) : null;
		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}

		ArrayList<Flights> departureFlightsList = FlightService.searchFlights
				(seatClass, departureAirport, arrivalAirport, departureDate, stopOverTimes, sortOption);
		request.setAttribute("departure-flights-list", departureFlightsList);

		if (roundTrip && returnDate != null) {
			ArrayList<Flights> returnFlightsList = FlightService.searchFlights
					(seatClass, arrivalAirport, departureAirport, returnDate, stopOverTimes, sortOption);
			request.setAttribute("return-flights-list", returnFlightsList);
		}

		getServletContext().getRequestDispatcher("/WEB-INF/pages/searchResult.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
