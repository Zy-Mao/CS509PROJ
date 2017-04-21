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
		String departureAirportCode = request.getParameter("departure-airport");
		String arrivalAirportCode = request.getParameter("arrival-airport");
		String departureDateString = request.getParameter("departure-date");
		String returnDateString = request.getParameter("return-date");
		Date departureDate = null;
		Date returnDate = null;
		logger.debug(departureAirportCode);
		logger.debug(departureDateString);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		try {
			departureDate = simpleDateFormat.parse(departureDateString);
			returnDate = simpleDateFormat.parse(returnDateString);
		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}

		Airport departureAirport = AirportService.getAirportByCode(departureAirportCode);
		Airport arrivalAirport = AirportService.getAirportByCode(arrivalAirportCode);

		ArrayList<Flights> flightsList = FlightService.searchFlightsWithNoLeg
				(departureAirport, arrivalAirport, departureDate, true);

		request.setAttribute("flights-list", flightsList);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/searchResult.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
