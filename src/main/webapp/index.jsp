<!DOCTYPE html>
<%@page import="com.wpi.teamd.airport.Airport" %>
<%@page import="com.wpi.teamd.airport.Airports" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.wpi.teamd.airport.Airport" %>
<%--<% Airports airports = (Airports) request.getAttribute("airports"); %>--%>
<% Airports airports = Airports.getInstance(); %>
<link rel="stylesheet" type="text/css" href="/static/css/combo.select.css"/>

<html>
<head>
	<meta charset="UTF-8">
	<title>WPI</title>
</head>

<body>
<div>
	<form action="search" method="post">
		<select name="departure-airport" style="width: 30%;">
			<option value="0">---</option>
			<% for (Iterator iterator = airports.iterator(); iterator.hasNext();) {
				Airport airport = (Airport) iterator.next();
			%>
			<option value="<%=airport.code()%>"><%=airport.code()%></option>
			<%
				}
			%>
		</select>

		<select name="arrival-airport" style="width: 30%;">
			<option value="0">---</option>
			<% for (Iterator iterator = airports.iterator(); iterator.hasNext();) {
				Airport airport = (Airport) iterator.next();
			%>
			<option value="<%=airport.code()%>"><%=airport.code()%></option>
			<%
				}
			%>
		</select>
		<input type="submit" value="Search">
	</form>

</div>
</body>

<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.combo.select.js"></script>
<script>
	$(function() {
		$('select').comboSelect();
	});
</script>

</html>