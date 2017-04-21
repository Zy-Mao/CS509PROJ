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

	<link rel="stylesheet" href="static/css/combo.select.css">
	<script src="static/js/jquery.combo.select.js"></script>
</head>

<style>
	body,h1,h2,h3,h4,h5,h6 {font-family: "Raleway", Arial, Helvetica, sans-serif}
</style>

<body class="w3-light-grey"/>

<style>
	.jumbotron {
		background-color: #66afe9; /* Orange */
		color: #000000;
	}
</style>

<body>
	<div class="jumbotron text-center">
		<div>
			<h1>WPI</h1>
			<p>Flight Ticket Reservation System</p>
		</div>
			<form action="search" method="post" id="search-form">
				<div class="w3-row-padding" style="margin:8px -16px;">
					<div class="w3-half w3-margin-bottom" align="right">
						<label style="display:block; width: 50%; text-align: left;">Departure Airport</label>
						<select class="w3-select w3-border" name="departure-airport" style="width: 50%; text-align: left;">
							<option value="" disabled selected hidden>Select Departure Airport</option>
							<% for (Iterator iterator = AirportService.getAirportPool().iterator(); iterator.hasNext();) {
								Airport airport = (Airport) iterator.next();
							%>
							<option value="<%=airport.code()%>"><%=airport.name() + ", " + airport.code()%></option>
							<%
								}
							%>
						</select>
					</div>
					<div class="w3-half w3-margin-bottom" align="left">
						<label style="display:block; width: 50%; text-align: left;">Arrival Airport</label>
						<select class="w3-select w3-border" name="arrival-airport" style="width: 50%; text-align: left;">
							<option value="" disabled selected>Select Arrival Airport</option>
							<% for (Iterator iterator = AirportService.getAirportPool().iterator(); iterator.hasNext();) {
								Airport airport = (Airport) iterator.next();
							%>
							<option value="<%=airport.code()%>"><%=airport.name() + ", " + airport.code()%></option>
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
							   value="2017-05-05" min="2017-05-05" max="2017-05-21" style="width: 50%;">
					</div>
					<div class="w3-half w3-margin-bottom">
						<label style="display:block; width: 50%; text-align: left;">Return Date</label>
						<input class="w3-input w3-border" type="date" name="return-date"
							   value="2017-05-06" min="2017-05-05" max="2017-05-21" style="width: 50%;">
					</div>
				</div>
			</form>
			<button class="w3-button w3-dark-grey" type="submit" form="search-form"><i class="fa fa-search w3-margin-right"></i> Search</button>
	</div>
</body>
<script>
	$(function(){
		$('select').comboSelect()
	})
</script>
</html>