<%@ page import="java.util.ArrayList" %>
<%@ page import="com.wpi.teamd.entity.Flights" %>
<%@ page import="com.wpi.teamd.entity.Flight" %>
<%--
  Created by IntelliJ IDEA.
  User: mao
  Date: 2017/4/21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<body>
<%	for (Flights flights : (ArrayList<Flights>) request.getAttribute("flights-list")) {
		for (Flight flight : flights) {
%>
<p><%=flight.getDepartAirport()%>, <%=flight.getArrivalAirport()%></p>
<%
			}
		}
%>
</body>
</html>
