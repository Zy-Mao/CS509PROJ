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
	<script>
		var selectedDepartureFlightsList = [];
		var selectedReturnFlightsList = [];
		var selectedDepartureTotalPrice = 0.0;
		var selectedReturnTotalPrice = 0.0;
		var getLock = 0;
		var secondCount = 0;
		var refreshIntervalId;
		var maxLockTime = 120;

		function deselectDepartureFlights() {
			$("#departure-flights-group").show();
			$("#return-flights-group").hide();
			$('#selected-departure-flights').hide();
			$('#selected-return-flights').hide();
			$('#flights-summary-panel').hide();
			selectedDepartureFlightsList = [];
			selectedDepartureTotalPrice = 0.0;
		}

		function deselectReturnFlights() {
			$("#return-flights-group").show();
			$('#selected-return-flights').hide();
			$('#flights-summary-panel').hide();
			selectedReturnFlightsList = [];
			selectedReturnTotalPrice = 0.0;
		}

		function lockOperation() {
			if (getLock == 0) {
				$.ajax({
					type: "POST",
					url: "/LockSeat",
					success: function (responseText) {
						getLock = 1;
						$("#lock-operation-button").text("Cancel");
						$("#confirm-order-button").prop('disabled', false);
						refreshIntervalId = setInterval(function () {
							secondCount += 1;
							$("#message-paragraph").text("Seat locked, " + (maxLockTime - secondCount) + " seconds remain...");
							if (secondCount >= maxLockTime) {
								$("#confirm-order-button").prop('disabled', true);
								clearInterval(refreshIntervalId);
								$.ajax({
									type: "POST",
									url: "/UnlockSeat",
									success: function (responseText) {
										getLock = 0;
										$("#lock-operation-button").text("Lock Seat");
									}
								});
								$("#message-paragraph").text("Seat lock released.");
								secondCount = 0;
							}
						}, 1000)
					}
				});
			} else {
				$.ajax({
					type: "POST",
					url: "/UnlockSeat",
					success: function (responseText) {
						getLock = 0;
						$("#confirm-order-button").prop('disabled', true);
						$("#lock-operation-button").text("Lock Seat");
						clearInterval(refreshIntervalId);
						$("#message-paragraph").text("Seat lock released.");
						secondCount = 0;
					}
				});
			}
		}

		function submitOrder() {
			$.ajax({
				type: "POST",
				url: "/ConfirmOrder",
				data: {
					departureFlights: JSON.stringify(selectedDepartureFlightsList),
					returnFlights: JSON.stringify(selectedReturnFlightsList)
				},
				success: function (responseText) {
					$.ajax({
						type: "POST",
						url: "/UnlockSeat",
						success: function (responseText) {
							getLock = 0;
							$("#confirm-order-button").prop('disabled', true);
							$("#lock-operation-button").text("Lock Seat");
							clearInterval(refreshIntervalId);
							secondCount = 0;
						}
					});
					$("#message-paragraph").text(responseText);
				}

			});
		}

		function getUrlParameters() {
			var vars = [], hash;
			var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
			for (var i = 0; i < hashes.length; i++) {
				hash = hashes[i].split('=');
				vars.push(hash[0]);
				vars[hash[0]] = hash[1];
			}
			return vars;
		}
	</script>
</head>

<body>

<jsp:include page="/index.jsp" flush="true"/>

<style>
	.w3-card-2 {
		margin: 15px 15px 15px 15px;
	}

	.w3-button {
		background-color: #66afe9;
	}
</style>

<h3 id="search-result-title" align="center">Search Result From
	<%=AirportService.getAirportByCode(request.getParameter("dp")).toString()%> To
	<%=AirportService.getAirportByCode(request.getParameter("ar")).toString()%>
</h3>

<div class="panel-group" id="selected-departure-flights">
	<div class="panel panel-default">
		<div class="panel-heading w3-card-2">
			<div class="panel-title">
				<div class="w3-panel" style="text-align:center">
					<div class="w3-quarter" id="departure-flights-stop-times-panel">
					</div>
					<div class="w3-quarter" id="departure-flights-time-panel">
					</div>
					<div class="w3-quarter" id="departure-duration-panel">
					</div>
					<div class="w3-quarter">
						<button class="w3-button w3-round" onclick="deselectDepartureFlights()">
							Cancel
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="w3-card-2 selected-departure-flights-sub-panel" id="selected-departure-flights-sub-panel-1">
			<ul class="list-group">
				<li class="list-group-item">
					<div class="w3-row-padding" style="text-align:center">
						<div class="w3-quarter airplane-info-div">
						</div>
						<div class="w3-quarter airport-info-div">
						</div>
						<div class="w3-quarter time-info-div">
						</div>
						<div class="w3-quarter price-info-div">
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div class="w3-card-2 selected-departure-flights-sub-panel" id="selected-departure-flights-sub-panel-2">
			<ul class="list-group">
				<li class="list-group-item">
					<div class="w3-row-padding" style="text-align:center">
						<div class="w3-quarter airplane-info-div">
						</div>
						<div class="w3-quarter airport-info-div">
						</div>
						<div class="w3-quarter time-info-div">
						</div>
						<div class="w3-quarter price-info-div">
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div class="w3-card-2 selected-departure-flights-sub-panel" id="selected-departure-flights-sub-panel-3">
			<ul class="list-group">
				<li class="list-group-item">
					<div class="w3-row-padding" style="text-align:center">
						<div class="w3-quarter airplane-info-div">
						</div>
						<div class="w3-quarter airport-info-div">
						</div>
						<div class="w3-quarter time-info-div">
						</div>
						<div class="w3-quarter price-info-div">
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>


<div class="panel-group" id="selected-return-flights">
	<div class="panel panel-default">
		<div class="panel-heading w3-card-2">
			<div class="panel-title">
				<div class="w3-panel" style="text-align:center">
					<div class="w3-quarter" id="return-flights-stop-times-panel">
					</div>
					<div class="w3-quarter" id="return-flights-time-panel">
					</div>
					<div class="w3-quarter" id="return-duration-panel">
					</div>
					<div class="w3-quarter">
						<button class="w3-button w3-round" onclick="deselectReturnFlights()">
							Cancel
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="w3-card-2 selected-return-flights-sub-panel" id="selected-return-flights-sub-panel-1">
			<ul class="list-group">
				<li class="list-group-item">
					<div class="w3-row-padding" style="text-align:center">
						<div class="w3-quarter airplane-info-div">
						</div>
						<div class="w3-quarter airport-info-div">
						</div>
						<div class="w3-quarter time-info-div">
						</div>
						<div class="w3-quarter price-info-div">
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div class="w3-card-2 selected-return-flights-sub-panel" id="selected-return-flights-sub-panel-2">
			<ul class="list-group">
				<li class="list-group-item">
					<div class="w3-row-padding" style="text-align:center">
						<div class="w3-quarter airplane-info-div">
						</div>
						<div class="w3-quarter airport-info-div">
						</div>
						<div class="w3-quarter time-info-div">
						</div>
						<div class="w3-quarter price-info-div">
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div class="w3-card-2 selected-return-flights-sub-panel" id="selected-return-flights-sub-panel-3">
			<ul class="list-group">
				<li class="list-group-item">
					<div class="w3-row-padding" style="text-align:center">
						<div class="w3-quarter airplane-info-div">
						</div>
						<div class="w3-quarter airport-info-div">
						</div>
						<div class="w3-quarter time-info-div">
						</div>
						<div class="w3-quarter price-info-div">
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
<div class="panel-group" id="flights-summary-panel">
	<div class="panel panel-default">
		<div class="panel-heading w3-card-2">
			<div class="panel-title">
				<div class="w3-panel" style="text-align:center">
					<div class="w3-quarter">
						<p id="message-paragraph"></p>
					</div>
					<div class="w3-quarter" id="total-price-panel">
						<p></p>
					</div>
					<div class="w3-quarter">
						<button class="w3-button w3-round" id="lock-operation-button" onclick="lockOperation()">
							Lock Seat
						</button>
					</div>
					<div class="w3-quarter">
						<button class="w3-button w3-round" id="confirm-order-button" onclick="submitOrder()" disabled>
							Confirm Order
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<div class="panel-group" id="departure-flights-group">
	<div class="panel panel-default">
		<%
			int count = 1;
			for (Flights flights : (ArrayList<Flights>) request.getAttribute("departure-flights-list")) {
		%>
		<div class="panel-heading w3-card-2 collapse-panel" data-toggle="collapse"
			 data-target="<%="#departure-flight-" + count%>">
			<div class="panel-title">
				<div class="w3-panel" style="text-align:center">
					<div class="w3-quarter"><%= flights.getStopTimes() + " Stop"%>
					</div>
					<div class="w3-quarter"><%= flights.getDepartureTimeInString() + " - " + flights.getArrivalTimeInString()%>
					</div>
					<div class="w3-quarter"><%= flights.getDurationInString()%>
					</div>
					<div class="w3-quarter">
						<button class="w3-button w3-round" id="<%="#departure-flight-button-" + count%>"
								onclick="selectDepartureFlight(
									<%=flights.getStopTimes()%>,
										'<%=flights.getDepartureTimeInString()%>',
										'<%=flights.getArrivalTimeInString()%>',
									<%=flights.getTotalPrice()%>,
										'<%=flights.getDurationInString()%>',
										[
									<% 	int flightsCount = 0;
											for (Map.Entry<Flight, Integer> entry : flights.entrySet()) {
												Flight flight = entry.getKey();%>
										{
										flightNo : '<%=flight.getNumber()%>',
										flightManufacture : '<%=flight.getAirplane().getManufacture()%>',
										flightModel : '<%=flight.getAirplane().getModel()%>',
										flightTime : <%=flight.getFlightTime()%>,
										flightDepartureAirport : '<%=flight.getDepartAirport().code()%>',
										flightArrivalAirport : '<%=flight.getArrivalAirport().code()%>',
										flightDepartureTime : '<%=flight.getDepartTimeInString(true)%>',
										flightArrivalTime : '<%=flight.getArrivalTimeInString(true)%>',
										flightDurationInString : '<%=flight.getDurationInString()%>',
										flightSeatClass : <%=entry.getValue()%>,
										flightSeatPrice : <%=entry.getValue() == 1 ?
											flight.getFirstClassPrice() : flight.getCoachClassPrice()%>,
										flightSeatRemain : <%=entry.getValue() == 1 ?
											flight.getAirplane().getFirstClassSeat() - flight.getFirstClassSeats() :
											flight.getAirplane().getCoachClassSeat() - flight.getCoachClassSeats()%>
									<%flightsCount++;%>
										}<%=flightsCount < flights.entrySet().size() ? "," : ""%>
									<%
											}
										%>
										])">
							<%= "$" + String.format("%.2f", flights.getTotalPrice())%>
						</button>
						<script>
							function selectDepartureFlight(flightsStopTimes, flightDepartureTime, flightArrivalTime, flightTotalPrice, flightDurationInString, flightList) {
//								$(".collapse-panel").collapse("hide");
								$('html, body').animate({
									scrollTop: $('#search-result-title').offset().top
								}, 700);
								$("#departure-flights-group").hide();
								var loopCount = 0;
								$('#selected-departure-flights').find(".selected-departure-flights-sub-panel").each(function () {
									if (loopCount < flightList.length) {
										var flight = flightList[loopCount];
										var flightInfo = {
											flightNo: flight.flightNo,
											flightSeatClass: flight.flightSeatClass,
										};
										selectedDepartureFlightsList.push(flightInfo);
										$(this).find('.airplane-info-div').html(
											'<p>' + 'Flight #' + flight.flightNo + '</p>' +
											'<p>' + flight.flightManufacture + ' ' + flight.flightModel + '</p>' +
											'<p>' + flight.flightTime + '</p>');
										$(this).find('.airport-info-div').html(
											'<p>' + flight.flightDepartureAirport + '-' + flight.flightArrivalAirport + '</p>' +
											'<p>' + flight.flightDepartureTime + '-' + flight.flightArrivalTime + '</p>');
										$(this).find('.time-info-div').html(
											'<p>' + flight.flightDurationInString + '</p>');
										$(this).find('.price-info-div').html(
											'<p>' + flight.flightSeatPrice + '</p>' +
											'<p>' + flight.flightSeatRemain + '</p>');
										$(this).show();
									}
									else {
										$(this).hide();
									}
									loopCount++;
								});
								$("#departure-flights-stop-times-panel").text(flightsStopTimes + " Stop");
								$("#departure-flights-time-panel").text(flightDepartureTime + "-" + flightArrivalTime);
								$("#departure-duration-panel").text(flightDurationInString);
								$('#selected-departure-flights').show();

								var urlParams = getUrlParameters();
								selectedDepartureTotalPrice = flightTotalPrice;
								if (urlParams["tr"] == "1") {
									$("#flights-summary-panel").show();
									$("#total-price-panel").html(
										'<p>' + 'Total Price' + '</p>' +
										'<p>' + selectedDepartureTotalPrice.toFixed(2) + '</p>'
									);
								} else {
									$("#return-flights-group").show();
								}
							}
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
							<p><%=flight.getDepartTimeInString(true) + " - " + flight.getArrivalTimeInString(true)%>
							</p>
						</div>
						<div class="w3-quarter">
							<p><%=flight.getDurationInString()%>
							</p>
						</div>
						<div class="w3-quarter">
							<p>
								<%="$" + String.format("%.2f",
									(entry.getValue() == 1 ? flight.getFirstClassPrice() : flight.getCoachClassPrice()))%>
							</p>
							<p>
								<%=(entry.getValue() == 1 ?
										flight.getAirplane().getFirstClassSeat() - flight.getFirstClassSeats() :
										flight.getAirplane().getCoachClassSeat() - flight.getCoachClassSeats())%>
							</p>
						</div>
					</div>
				</li>
				<%
					}
				%>
			</ul>
		</div>
		<%
				count++;
			}
		%>
	</div>
</div>

<div class="panel-group" id="return-flights-group">
	<div class="panel panel-default">
		<%
			count = 1;
			ArrayList<Flights> returnFlightsList = (ArrayList<Flights>) request.getAttribute("return-flights-list");
			if (returnFlightsList != null) {
				for (Flights flights : returnFlightsList) {
		%>
		<div class="panel-heading w3-card-2 collapse-panel" data-toggle="collapse"
			 data-target="<%="#return-flight-" + count%>">
			<div class="panel-title">
				<div class="w3-panel" style="text-align:center">
					<div class="w3-quarter"><%= flights.getStopTimes() + " Stop"%>
					</div>
					<div class="w3-quarter"><%= flights.getDepartureTimeInString() + " - " + flights.getArrivalTimeInString()%>
					</div>
					<div class="w3-quarter"><%= flights.getDurationInString()%>
					</div>
					<div class="w3-quarter">
						<button class="w3-button w3-round" id="<%="#return-flight-button-" + count%>"
								onclick="selectReturnFlight(<%=flights.getStopTimes()%>,
										'<%=flights.getDepartureTimeInString()%>',
										'<%=flights.getArrivalTimeInString()%>',
										'<%=flights.getDurationInString()%>',
									<%=flights.getTotalPrice()%>,
										[
									<% 	int flightsCount = 0;
								for (Map.Entry<Flight, Integer> entry : flights.entrySet()) {
									Flight flight = entry.getKey();%>
										{
										flightNo : '<%=flight.getNumber()%>',
										flightManufacture : '<%=flight.getAirplane().getManufacture()%>',
										flightModel : '<%=flight.getAirplane().getModel()%>',
										flightTime : <%=flight.getFlightTime()%>,
										flightDepartureAirport : '<%=flight.getDepartAirport().code()%>',
										flightArrivalAirport : '<%=flight.getArrivalAirport().code()%>',
										flightDepartureTime : '<%=flight.getDepartTimeInString(true)%>',
										flightArrivalTime : '<%=flight.getArrivalTimeInString(true)%>',
										flightDurationInString : '<%=flight.getDurationInString()%>',
										flightSeatClass : <%=entry.getValue()%>,
										flightSeatPrice : <%=entry.getValue() == 1 ?
								flight.getFirstClassPrice() : flight.getCoachClassPrice()%>,
										flightSeatRemain : <%=entry.getValue() == 1 ?
								flight.getAirplane().getFirstClassSeat() - flight.getFirstClassSeats() :
								flight.getAirplane().getCoachClassSeat() - flight.getCoachClassSeats()%>
									<%flightsCount++;%>
										}<%=flightsCount < flights.entrySet().size() ? "," : ""%>
									<%
								}
							%>
										])">
							<%= "$" + String.format("%.2f", flights.getTotalPrice())%>
						</button>
						<script>
							function selectReturnFlight(flightsStopTimes,
														flightDepartureTime,
														flightArrivalTime,
														flightDurationInString,
														flightTotalPrice,
														flightList) {
//								$(".collapse-panel").collapse("hide");
								$('html, body').animate({
									scrollTop: $('#search-result-title').offset().top
								}, 700);
								$("#return-flights-group").hide();
								var loopCount = 0;
								$('#selected-return-flights').find(".selected-return-flights-sub-panel").each(function () {
									if (loopCount < flightList.length) {
										var flight = flightList[loopCount];
										var flightInfo = {
											flightNo: flight.flightNo,
											flightSeatClass: flight.flightSeatClass,
										};
										selectedReturnFlightsList.push(flightInfo);
										$(this).find('.airplane-info-div').html(
											'<p>' + 'Flight #' + flight.flightNo + '</p>' +
											'<p>' + flight.flightManufacture + ' ' + flight.flightModel + '</p>' +
											'<p>' + flight.flightTime + '</p>');
										$(this).find('.airport-info-div').html(
											'<p>' + flight.flightDepartureAirport + '-' + flight.flightArrivalAirport + '</p>' +
											'<p>' + flight.flightDepartureTime + '-' + flight.flightArrivalTime + '</p>');
										$(this).find('.time-info-div').html(
											'<p>' + flight.flightDurationInString + '</p>');
										$(this).find('.price-info-div').html(
											'<p>' + flight.flightSeatPrice + '</p>' +
											'<p>' + flight.flightSeatRemain + '</p>');
										$(this).show();
									}
									else {
										$(this).hide();
									}
									loopCount++;
								});
								$("#return-flights-stop-times-panel").text(flightsStopTimes + " Stop");
								$("#return-flights-time-panel").text(flightDepartureTime + "-" + flightArrivalTime);
								$("#return-duration-panel").text(flightDurationInString);
								$('#selected-return-flights').show();

								var urlParams = getUrlParameters();
								selectedReturnTotalPrice = flightTotalPrice;
								totalPrice = selectedDepartureTotalPrice + selectedReturnTotalPrice;
								$("#flights-summary-panel").show();
								$("#total-price-panel").html(
									'<p>' + 'Total Price' + '</p>' +
									'<p>' + totalPrice.toFixed(2) + '</p>'
								);
							}

						</script>
					</div>
				</div>
			</div>
		</div>

		<div id="<%="return-flight-" + count%>" class="panel-collapse collapse w3-card-2">
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
							<p><%=flight.getDepartTimeInString(true) + " - " + flight.getArrivalTimeInString(true)%>
							</p>
						</div>
						<div class="w3-quarter">
							<p><%=flight.getDurationInString()%>
							</p>
						</div>
						<div class="w3-quarter">
							<p>
								<%="$" + String.format("%.2f",
										(entry.getValue() == 1 ? flight.getFirstClassPrice() : flight.getCoachClassPrice()))%>
							</p>
							<p>
								<%=(entry.getValue() == 1 ?
										flight.getAirplane().getFirstClassSeat() - flight.getFirstClassSeats() :
										flight.getAirplane().getCoachClassSeat() - flight.getCoachClassSeats())%>
							</p>
						</div>
					</div>
				</li>
				<%
					}
				%>
			</ul>
		</div>
		<%
					count++;
				}
			}
		%>
	</div>
</div>
<script>
	$("#return-flights-group").hide();
	$("#flights-summary-panel").hide();
	$("#selected-departure-flights").hide();
	$("#selected-return-flights").hide();
</script>
</body>
</html>
