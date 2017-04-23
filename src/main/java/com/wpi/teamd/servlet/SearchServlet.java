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

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean roundTrip = request.getParameter("tr") != null
				&& request.getParameter("tr").equals("2");
		Integer seatClass = request.getParameter("sc") != null
				&& request.getParameter("sc").equals("1") ? 1 : 2;
		String departureAirportCode = request.getParameter("dp");
		String arrivalAirportCode = request.getParameter("ar");
		String departureDateString = request.getParameter("dd");
		String returnDateString = request.getParameter("rd");
		Integer stopOverTimes = request.getParameter("sp") != null ?
				Integer.parseInt(request.getParameter("sp")) : 0;
		String sortOption = request.getParameter("st");
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

		logger.debug(1);
		getServletContext().getRequestDispatcher("/WEB-INF/pages/searchResult.jsp").forward(request, response);
	}
}
