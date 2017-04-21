package com.wpi.teamd.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mao on 2017/4/16.
 */
@WebServlet(name = "WelcomeServlet", urlPatterns={"/1"})
public class WelcomeServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(WelcomeServlet.class);

	public void init() {

	}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Airports airports = Airports.getInstance();
//		request.setAttribute("airports", airports);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);

		String name = request.getParameter("name");

//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        out.println("<b>Hello.</b>");
//        out.close();
    }
}
