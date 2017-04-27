package com.wpi.teamd.servlet;

import com.wpi.teamd.dao.ServerInterface;
import com.wpi.teamd.service.FlightService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by mao on 2017/4/20.
 */
@WebServlet(name = "ConfirmOrderServlet", urlPatterns = {"/ConfirmOrder"})
public class ConfirmOrderServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(ConfirmOrderServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		logger.debug(request.getParameter("dpFlightNo1"));
		LinkedHashMap<String, String> flightsOrderList = new LinkedHashMap<>();
		if (request.getParameter("dpFlightNo1") != null && !request.getParameter("dpFlightNo1").equals("")
				&& request.getParameter("dpFlightSeatClass1") != null && !request.getParameter("dpFlightSeatClass1").equals("")) {
			flightsOrderList.put(request.getParameter("dpFlightNo1"), request.getParameter("dpFlightSeatClass1"));
		}
		if (request.getParameter("dpFlightNo2") != null && !request.getParameter("dpFlightNo2").equals("")
				&& request.getParameter("dpFlightSeatClass2") != null && !request.getParameter("dpFlightSeatClass2").equals("")) {
			flightsOrderList.put(request.getParameter("dpFlightNo2"), request.getParameter("dpFlightSeatClass2"));
		}
		if (request.getParameter("dpFlightNo3") != null && !request.getParameter("dpFlightNo3").equals("")
				&& request.getParameter("dpFlightSeatClass3") != null && !request.getParameter("dpFlightSeatClass3").equals("")) {
			flightsOrderList.put(request.getParameter("dpFlightNo3"), request.getParameter("dpFlightSeatClass3"));
		}
		if (request.getParameter("rtFlightNo1") != null && !request.getParameter("rtFlightNo1").equals("")
				&& request.getParameter("rtFlightSeatClass1") != null && !request.getParameter("rtFlightSeatClass1").equals("")) {
			flightsOrderList.put(request.getParameter("rtFlightNo1"), request.getParameter("rtFlightSeatClass1"));
		}
		if (request.getParameter("rtFlightNo2") != null && !request.getParameter("rtFlightNo2").equals("")
				&& request.getParameter("rtFlightSeatClass2") != null && !request.getParameter("rtFlightSeatClass2").equals("")) {
			flightsOrderList.put(request.getParameter("rtFlightNo2"), request.getParameter("rtFlightSeatClass2"));
		}
		if (request.getParameter("rtFlightNo3") != null && !request.getParameter("rtFlightNo3").equals("")
				&& request.getParameter("rtFlightSeatClass3") != null && !request.getParameter("rtFlightSeatClass3").equals("")) {
			flightsOrderList.put(request.getParameter("rtFlightNo3"), request.getParameter("rtFlightSeatClass3"));
		}

//		flightsOrderList.put((request.getParameter("dpFlightNo1") != null ? request.getParameter("dpFlightNo1") : ""),
//				(request.getParameter("dpFlightSeatClass1") != null ? request.getParameter("dpFlightSeatClass1") : ""));
//		flightsOrderList.put((request.getParameter("dpFlightNo2") != null ? request.getParameter("dpFlightNo2") : ""),
//				(request.getParameter("dpFlightSeatClass2") != null ? request.getParameter("dpFlightSeatClass2") : ""));
//		flightsOrderList.put((request.getParameter("dpFlightNo3") != null ? request.getParameter("dpFlightNo3") : ""),
//				(request.getParameter("dpFlightSeatClass3") != null ? request.getParameter("dpFlightSeatClass3") : ""));
//		flightsOrderList.put((request.getParameter("rtFlightNo1") != null ? request.getParameter("rtFlightNo1") : ""),
//				(request.getParameter("rtFlightSeatClass1") != null ? request.getParameter("rtFlightSeatClass1") : ""));
//		flightsOrderList.put((request.getParameter("rtFlightNo2") != null ? request.getParameter("rtFlightNo2") : ""),
//				(request.getParameter("rtFlightSeatClass2") != null ? request.getParameter("rtFlightSeatClass2") : ""));
//		flightsOrderList.put((request.getParameter("rtFlightNo3") != null ? request.getParameter("rtFlightNo3") : ""),
//				(request.getParameter("rtFlightSeatClass3") != null ? request.getParameter("rtFlightSeatClass3") : ""));
		if (ServerInterface.lock()) {
			if (FlightService.reserveSeat(flightsOrderList)) {
				ServerInterface.unlock();
				getServletContext().getRequestDispatcher("/WEB-INF/pages/orderSuccess.jsp").forward(request, response);
			} else {
				ServerInterface.unlock();
				getServletContext().getRequestDispatcher("/WEB-INF/pages/orderFailed.jsp").forward(request, response);
			}
		} else {
			getServletContext().getRequestDispatcher("/WEB-INF/pages/confirmOrder.jsp").forward(request, response);
		}

//		String roundTrip = request.getParameter("tr") != null
//				&& request.getParameter("tr").equals("2");
//		Boolean roundTrip = request.getParameter("tr") != null
//				&& request.getParameter("tr").equals("2");
//		Integer seatClass = request.getParameter("sc") != null
//				&& request.getParameter("sc").equals("1") ? 1 : 2;
//		String departureAirportCode = request.getParameter("dp");
//		String arrivalAirportCode = request.getParameter("ar");
//		String departureDateString = request.getParameter("dd");
//		String returnDateString = request.getParameter("rd");
//		Integer stopOverTimes = request.getParameter("sp") != null ?
//				Integer.parseInt(request.getParameter("sp")) : 0;
//		String sortOption = request.getParameter("st");
//		if (sortOption == null) sortOption = "price";
//
//		Airport departureAirport = AirportService.getAirportByCode(departureAirportCode);
//		Airport arrivalAirport = AirportService.getAirportByCode(arrivalAirportCode);
//
//		Date departureDate = null;
//		Date returnDate = null;
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
//		try {
//			departureDate = simpleDateFormat.parse(departureDateString);
//			returnDate = returnDateString != null ? simpleDateFormat.parse(returnDateString) : null;
//		} catch (ParseException e) {
//			logger.debug(e.getMessage());
//		}
//
//		ArrayList<Flights> departureFlightsList = FlightService.searchFlights
//				(seatClass, departureAirport, arrivalAirport, departureDate, stopOverTimes, sortOption);
//		request.setAttribute("departure-flights-list", departureFlightsList);
//
//		if (roundTrip && returnDate != null) {
//			ArrayList<Flights> returnFlightsList = FlightService.searchFlights
//					(seatClass, arrivalAirport, departureAirport, returnDate, stopOverTimes, sortOption);
//			request.setAttribute("return-flights-list", returnFlightsList);
//		}

//		getServletContext().getRequestDispatcher("/WEB-INF/pages/confirmOrder.jsp").forward(request, response);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
