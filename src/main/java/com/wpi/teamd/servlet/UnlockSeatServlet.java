package com.wpi.teamd.servlet;

import com.wpi.teamd.dao.ServerInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class would process the unlock request from web page.
 *
 * @author Mao
 * @version 1.0
 * @since 2017-04-20
 *
 *
 *
 */
@WebServlet(name = "UnlockSeatServlet", urlPatterns = {"/UnlockSeat"})
public class UnlockSeatServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(LockSeatServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseText = "1";
		ServerInterface.unlock();
		response.setContentType("text/plain");
		response.getWriter().write(responseText);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
