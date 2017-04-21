package com.wpi.teamd.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mao on 2017/4/20.
 */
@WebServlet(name = "SearchServlet", urlPatterns={"/search"})
public class SearchServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(SearchServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String departureAirportCode = request.getParameter("departure-airport");
		String arrivalAirportCode = request.getParameter("arrival-airport");
		String departureDate = request.getParameter("departure-date");
		String arrivalDate = request.getParameter("arrival-date");
		logger.debug(departureAirportCode);
		logger.debug(departureDate);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
