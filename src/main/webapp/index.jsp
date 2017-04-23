<!DOCTYPE html>
<html lang="en">

<%@page import="com.wpi.teamd.entity.Airport" %>
<%@page import="com.wpi.teamd.service.AirportService" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.wpi.teamd.service.AirplaneService" %>

<html>
<head>
	<title>WPI</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

	<%--<link rel="stylesheet" href="static/css/combo.select.css">--%>
	<%--<script src="static/js/jquery.combo.select.js"></script>--%>

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>

	<!-- (Optional) Latest compiled and minified JavaScript translation files -->
	<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/i18n/defaults-*.min.js"></script>--%>

	<link href="static/css/icheck_minimal/grey.css" rel="stylesheet">
	<script src="your-path/icheck.js"></script>

</head>

<style>
	body,h1,h2,h3,h4,h5,h6 {font-family: "Raleway", Arial, Helvetica, sans-serif}
</style>

<body class="w3-light-grey"/>

<style>
	.jumbotron {
		background-color: #66afe9;
		color: #000000;
	}
</style>

<body>
	<div class="jumbotron text-center">
		<div>
			<a href=""><img src="image/logo.png" style="width: 15%"/></a>
			<a href="" style="text-decoration:none;"><h1>WPI</h1></a>
			<p>Flight Ticket Reservation System</p>
		</div>
			<form action="search" method="post" id="search-form">

				<div class="w3-row-padding" style="margin:8px -16px;">
					<div class="col-sm-3"></div>
					<div class="col-sm-3">
						<input class="w3-radio" type="radio" name="trip-route" value="one-way" checked/>
						<label>One Way</label>
						<input class="w3-radio" type="radio" name="trip-route" value="round-trip"/>
						<label>Round Trip</label>
						<script>
							$('input:radio').on('change', function(){
								var return_date_radio = $("#return-date")
								if ($(this).val() == "one-way") {
									return_date_radio.prop("disabled", true);
									return_date_radio.css("color", "grey");
								} else {
									return_date_radio.prop("disabled", false);
									return_date_radio.css("color", "black");
								}
							});
						</script>
					</div>
					<div class="col-sm-3">
						<input class="w3-radio w3-border" type="radio" name="seat-class" value="first-class" checked/>First Class
						<input class="w3-radio w3-border" type="radio" name="seat-class" value="coach-class"/>Coach Class
					</div>
					<div class="col-sm-3"></div>
				</div>

				<div class="w3-row-padding" style="margin:8px -16px;">
					<div class="w3-half w3-margin-bottom" align="right">
						<label for="departure-airport" style="display:block; width: 50%; text-align: left;">Departure Airport</label>
						<select class="selectpicker w3-border" id="departure-airport" name="departure-airport"
								data-live-search="true" title="Select departure airport..."
								data-width="50%" data-show-subtext="true">
							<% for (Airport airport : AirportService.getAirportPool()) {
								String selected = request.getParameter("departure-airport") != null
										&& request.getParameter("departure-airport").equals(airport.code()) ? "selected" : "";
							%>
							<option value="<%=airport.code()%>" data-subtext=<%=airport.code()%> <%=selected%>><%=airport.name()%></option>
							<%
								}
							%>
						</select>
					</div>
					<div class="w3-half w3-margin-bottom" align="left">
						<label style="display:block; width: 50%; text-align: left;">Arrival Airport</label>
						<select class="selectpicker w3-border" name="arrival-airport"
								data-live-search="true" title="Select arrival airport..."
								data-width="50%" data-show-subtext="true">
							<% for (Airport airport : AirportService.getAirportPool()) {
								String selected = request.getParameter("arrival-airport") != null
										&& request.getParameter("arrival-airport").equals(airport.code()) ? "selected" : "";
							%>
							<option value="<%=airport.code()%>" data-subtext=<%=airport.code()%> <%=selected%>><%=airport.name()%></option>
							<%
								}
							%>
						</select>
					</div>
				</div>

				<div class="w3-row-padding" style="margin:8px -16px;">
					<div class="w3-half w3-margin-bottom" align="right">
						<label style="display:block; width: 50%; text-align: left;">Departure Date</label>
						<input class="w3-input w3-border" type="date" name="departure-date"
							   <% String departureDate = request.getParameter("departure-date"); %>
							   value="<%=departureDate != null ? departureDate : "2017-05-05"%>" style="width: 50%;">
					</div>
					<div class="w3-half w3-margin-bottom" align="left">
						<label style="display:block; width: 50%; text-align: left;">Return Date</label>
						<input class="w3-input w3-border" type="date" name="return-date" id="return-date"
							<% String returnDate = request.getParameter("return-date"); %>
							   value="<%=returnDate != null ? returnDate : "2017-05-06"%>" disabled style="width: 50%; color: grey">
					</div>
				</div>

				<div class="w3-row-padding" style="margin:8px -16px;">
					<div class="w3-half w3-margin-bottom" align="right">
						<label style="display:block; width: 50%; text-align: left;">Stop Over Times</label>
						<select class="selectpicker" data-width="50%">
							<option>0 Stop</option>
							<option>1 Stop</option>
							<option>2 Stop</option>
						</select>
					</div>
					<div class="w3-half w3-margin-bottom" align="left">
						<label style="display:block; width: 50%; text-align: left;">Sort By</label>
						<select class="selectpicker" data-width="50%">
							<option>Duration</option>
							<option>Price</option>
							<option>StopOver</option>
						</select>
					</div>
				</div>

			</form>
			<button class="w3-button w3-dark-grey" type="submit" form="search-form"><i class="fa fa-search w3-margin-right"></i> Search</button>
	</div>
</body>
</html>