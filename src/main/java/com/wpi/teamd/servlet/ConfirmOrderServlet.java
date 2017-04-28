package com.wpi.teamd.servlet;

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
		LinkedHashMap<String, String> flightsOrderList = new LinkedHashMap<>();
		String responseText = "";


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

		if (FlightService.reserveSeat((flightsOrderList))) {
			responseText = "Order success.";
		} else {
			responseText = "Order failed.";
		}
		response.setContentType("text/plain");
		response.getWriter().write(responseText);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
