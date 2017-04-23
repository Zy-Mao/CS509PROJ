<%@ page import="java.util.ArrayList" %>
<%@ page import="com.wpi.teamd.entity.Flights" %>
<%@ page import="com.wpi.teamd.entity.Flight" %>
<%@ page import="java.util.Map" %>
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
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<style>
		html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
	</style>
</head>
<body class="w3-light-grey">

<body>

<jsp:include page="/index.jsp" flush="true"/>

<div class="w3-container">
	<h5>Recent Users</h5>
	<ul class="w3-ul w3-card-4 w3-white">

		<%	for (Flights flights : (ArrayList<Flights>) request.getAttribute("departure-flights-list")) {
			for (Map.Entry<Flight, Integer> entry : flights.entrySet()) {
				Flight flight = entry.getKey();
		%>
		<li>
			<%=flight.getDepartAirport().code()%>, <%=flight.getArrivalAirport().code()%>
			<%=flight.getDepartTime()%>, <%=flight.getArrivalTime()%>
		</li>
		<%
				}
			}
		%>
		<%--<li class="w3-padding-16">--%>
			<%--<img src="/w3images/avatar2.png" class="w3-left w3-circle w3-margin-right" style="width:35px">--%>
			<%--<span class="w3-xlarge">Mike</span><br>--%>
		<%--</li>--%>
	</ul>
</div>


</body>
</html>
