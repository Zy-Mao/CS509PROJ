package com.wpi.teamd.servlet;

import com.wpi.teamd.service.FlightService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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

		buildFlightsList(flightsOrderList, request.getParameter("departureFlights"));
		buildFlightsList(flightsOrderList, request.getParameter("returnFlights"));

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

	private void buildFlightsList(HashMap<String, String> flightsOrderList, String inputJsonString) {
		logger.debug(inputJsonString);
		JSONArray jsonArray = new JSONArray(inputJsonString);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String flightNo = jsonObject.getString("flightNo");
			String flightSeatClass = Integer.toString(jsonObject.getInt("flightSeatClass"));
			flightsOrderList.put(flightNo, flightSeatClass);
		}
	}
}
