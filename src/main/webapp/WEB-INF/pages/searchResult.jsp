<%@ page import="com.wpi.teamd.entity.Flight" %>
<%@ page import="com.wpi.teamd.entity.Flights" %>
<%@ page import="com.wpi.teamd.service.AirportService" %>
<%@ page import="java.util.ArrayList" %>
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
<%--<body class="w3-light-grey">--%>

<body>

<jsp:include page="/index.jsp" flush="true"/>


<h3 align="center">Search Result From
	<%=AirportService.getAirportByCode(request.getParameter("dp")).toString()%> To
	<%=AirportService.getAirportByCode(request.getParameter("ar")).toString()%>
</h3>
<div class="panel-group">
	<div class="panel panel-default">
		<%
			int count = 1;
			for (Flights flights : (ArrayList<Flights>) request.getAttribute("departure-flights-list")) {
		%>
		<style>
			.w3-card-2 {
				/*border: 1px solid black;*/
				margin: 15px 15px 15px 15px;
			}

			.w3-button {
				background-color: #66afe9;
			}
		</style>
		<div class="panel-heading w3-card-2" data-toggle="collapse" data-target="<%="#departure-flight-" + count%>">
			<div class="panel-title">
				<div class="w3-panel" style="text-align:center">
					<div class="w3-quarter"><%= flights.getStopTimes() + " Stop"%>
					</div>
					<div class="w3-quarter"><%= flights.getDepartureTime() + " - " + flights.getArrivalTime()%>
					</div>
					<div class="w3-quarter"><%= flights.getDurationInString()%>
					</div>
					<div class="w3-quarter">
						<button class="w3-button w3-round" id="<%="#departure-flight-button-" + count%>">
							<%= "$" + String.format("%.2f", flights.getTotalPrice())%>
						</button>
						<script>
							$("#").click(function () {
								//TODO
								alert("Handler for .click() called.");
							});
						</script>
					</div>
				</div>
			</div>
		</div>

		<div id="<%="departure-flight-" + count%>" class="panel-collapse collapse w3-card-2">
			<ul class="list-group">
				<%
					for (Map.Entry<Flight, Integer> entry : flights.entrySet()) {
						Flight flight = entry.getKey();
				%>
				<li class="list-group-item">
					<div class="w3-row-padding" style="text-align:center">
						<div class="w3-quarter">
							<p><%="Flight #" + flight.getNumber()%>
							</p>
							<p><%=flight.getAirplane().getManufacture() + " " + flight.getAirplane().getModel()%>
							</p>
							<p><%=flight.getFlightTime()%>
							</p>
						</div>
						<div class="w3-quarter">
							<p><%=flight.getDepartAirport().code() + " - " + flight.getArrivalAirport().code()%>
							</p>
							<p><%=flight.getDepartTime() + " - " + flight.getArrivalTime()%>
							</p>
						</div>
						<div class="w3-quarter">
							<p><%=flight.getDurationInString()%>
							</p>
						</div>
						<div class="w3-quarter">
							<%="$" + String.format("%.2f",
									(entry.getValue() == 1 ? flight.getFirstClassPrice() : flight.getCoachClassPrice()))%>
						</div>
					</div>
				</li>
				<%
					}
				%>
				<%--<div class="panel-footer">Footer</div>--%>
			</ul>

		</div>

		<%
				count++;
			}
		%>
	</div>
</div>


<%--<script>--%>
<%--$('.majorpoint').click(function(){--%>
<%--$(document).find('.hider').toggle();--%>
<%--});--%>
<%--</script>--%>


</body>
</html>
