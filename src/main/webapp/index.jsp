<!DOCTYPE html>
<html lang="en">

<%@page import="com.wpi.teamd.entity.Airport" %>
<%@page import="com.wpi.teamd.service.AirportService" %>

<html>
<head>
	<title>WPI</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>


	<link href="static/css/icheck_minimal/grey.css" rel="stylesheet">
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

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
			<a href="/"><img src="image/logo.png" style="width: 15%"/></a>
			<a href="/" style="text-decoration:none;"><h1>WPI</h1></a>
			<p>Flight Ticket Reservation System</p>
		</div>
			<form action="search" id="search-form" method="get">

				<div class="w3-row-padding" style="margin:8px -16px;">
					<div class="col-sm-3"></div>
					<div class="col-sm-3">
						<%
							String checked_tr = request.getParameter("tr") != null
									&& request.getParameter("tr").equals("2") ? "2" : "1";
						%>
						<input class="w3-radio" type="radio" name="tr" value="1" <%= checked_tr.equals("1") ? "checked" : ""%>/>
						<label>One Way</label>
						<input class="w3-radio" type="radio" name="tr" value="2" <%= checked_tr.equals("2") ? "checked" : ""%>/>
						<label>Round Trip</label>
						<script>
							$('input:radio').on('change', function(){
								var return_date_input = $("#return-date");
								if ($(this).val() == "1") {
									return_date_input.prop("disabled", true);
									return_date_input.css("color", "grey");
									$( "#slider-range2" ).slider('disable');
								} else {
									return_date_input.prop("disabled", false);
									return_date_input.css("color", "black");
									$( "#slider-range2" ).slider('enable');
								}
							});
						</script>
					</div>
					<div class="col-sm-3">
						<%
							String checked_sc = request.getParameter("sc") != null
									&& request.getParameter("sc").equals("2") ? "2" : "1";
						%>
						<input class="w3-radio w3-border" type="radio" name="sc" value="1" <%= checked_sc.equals("1") ? "checked" : ""%>/>
						<label>First Class</label>
						<input class="w3-radio w3-border" type="radio" name="sc" value="2" <%= checked_sc.equals("2") ? "checked" : ""%>/>
						<label>Coach Class</label>
					</div>
					<div class="col-sm-3">
						<button class="w3-button w3-small w3-black w3-round-xxlarge" type="button" onclick="hide_reveal()" >timeWindow</button>
						<script>
							function hide_reveal(){
								$( "#time-window" ).slideToggle();
								//var hidden = document.getElementById("time-window").style.display;
								//if (hidden == "none"){
								//	$( "#time-window" ).show();
								//}
								//else{
								//	$( "#time-window" ).hide();
								//}
							}
						</script>
					</div>
				</div>

				<div class="w3-row-padding" style="margin:8px -16px;">
					<div class="w3-half w3-margin-bottom" align="right">
						<label style="display:block; width: 50%; text-align: left;">Departure Airport</label>
						<select id="depart_airport" class="selectpicker w3-border" name="dp"
								data-live-search="true" title="Select departure airport..."
								data-width="50%" data-show-subtext="true">
							<% for (Airport airport : AirportService.getAirportPool()) {
								String selected = request.getParameter("dp") != null
										&& request.getParameter("dp").equals(airport.code()) ? "selected" : "";
							%>
							<option value="<%=airport.code()%>" data-subtext=<%=airport.code()%> <%=selected%>><%=airport.name()%></option>
							<%
								}
							%>
						</select>
					</div>
					<div class="w3-half w3-margin-bottom" align="left">
						<label style="display:block; width: 50%; text-align: left;">Arrival Airport</label>
						<select id="arrival_airport" class="selectpicker w3-border" name="ar"
								data-live-search="true" title="Select arrival airport..."
								data-width="50%" data-show-subtext="true">
							<% for (Airport airport : AirportService.getAirportPool()) {
								String selected = request.getParameter("ar") != null
										&& request.getParameter("ar").equals(airport.code()) ? "selected" : "";
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
						<input class="w3-input w3-border" type="date" name="dd" id="depart-date"
							   <% String departureDate = request.getParameter("dd"); %>
							   value="<%=departureDate != null ? departureDate : "2017-05-05"%>" style="width: 50%;">
					</div>
					<div class="w3-half w3-margin-bottom" align="left">
						<%
							String returnDate = request.getParameter("rd");
							String tripRoute = request.getParameter("tr");
						%>
						<label style="display:block; width: 50%; text-align: left;">Return Date</label>
						<input class="w3-input w3-border" type="date" name="rd" id="return-date"
							   value="<%=returnDate != null ? returnDate : "2017-05-12"%>"
							<%=tripRoute == null || tripRoute.equals("1") ? "disabled" : "" %>
							   style="width: 50%; color: <%=tripRoute == null || tripRoute.equals("1") ? "grey" : "black" %>">
					</div>
      			</div>
      			
      			
      			<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
				<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
				<script>
					$( function() {
						$( "#slider-range" ).slider({
							range: true,
							animate: true,
							min: 0,
							max: 23,
							values: [ 0, 23 ],
							slide: function( event, ui ) {
								$( "#dep_time" ).val( ui.values[ 0 ] + ":00 -" + ui.values[ 1 ] + ":59" );
							}
						});
						
						$( "#dep_time" ).val( $( "#slider-range" ).slider( "values", 0 ) +
							":00 -" + $( "#slider-range" ).slider( "values", 1 ) + ":59" );
					} );
				</script>
				<script>
					$( function() {
						$( "#slider-range2" ).slider({
							range: true,
							animate: true,	
							min: 0,
							max: 23,
							values: [ 0, 23 ],
							slide: function( event, ui ) {
								$( "#ret_time" ).val( ui.values[ 0 ] + ":00 -" + ui.values[ 1 ] + ":59" );
							}
						});
						
						$( "#ret_time" ).val( $( "#slider-range2" ).slider( "values", 0 ) +
							":00 -" + $( "#slider-range2" ).slider( "values", 1 ) + ":59" );
						$( "#slider-range2" ).slider('disable');
								
					} );
				</script>
      			
					
				<div class="w3-row" style="margin:4px -16px; display:none" id = time-window>
					<div class="w3-quarter w3-container"></div>
					<div class="w3-quarter w3-container">
						<p>
							<label style="font-size-adjust: 0.5;" for="dep_time">Time Window:</label>
         					<input type = "text" id = "dep_time" readonly = "true"
            					style = "font-weight:bold; color:#006000; width:120px; height:30px">
      					</p>
      					<div id = "slider-range"></div>
      				</div>
					<div class="w3-quarter w3-container">
						<p>
         					<label for = "ret_time">Time Window:</label>
         					<input type = "text" id = "ret_time" readonly = "true"
            					style = "font-weight:bold; color:#5B00AE; width:120px; height:30px">
      					</p>
      					<div id = "slider-range2"></div>
      				</div>
      				<div class="w3-quarter w3-container"></div>				
				</div>


				<div class="w3-row-padding" style="margin:8px -16px;">
					<div class="w3-half w3-margin-bottom" align="right">
						<%
							String selected_sp = request.getParameter("sp");
							if (selected_sp == null) selected_sp = "0";
						%>
						<label style="display:block; width: 50%; text-align: left;">Max Stop Over Times</label>
						<select class="selectpicker" name="sp" data-width="50%">
							<option value="0" <%= selected_sp.equals("0") ? "selected" : ""%>>0 Stop</option>
							<option value="1" <%= selected_sp.equals("1") ? "selected" : ""%>>1 Stop</option>
							<option value="2" <%= selected_sp.equals("2") ? "selected" : ""%>>2 Stop</option>
						</select>
					</div> 
				 	<div class="w3-half w3-margin-bottom" align="left">
						<%
							String selected_st = request.getParameter("st");
							if (selected_st == null) selected_st = "price";
						%>
						<label style="display:block; width: 50%; text-align: left;">Sort By</label>
						<select class="selectpicker" name="st" data-width="50%">
							<option value="price" <%= selected_st.equals("price") ? "selected" : ""%>>Price</option>
							<option value="duration" <%= selected_st.equals("duration") ? "selected" : ""%>>Duration</option>
							<option value="stop" <%= selected_st.equals("stop") ? "selected" : ""%>>Max Stop Over Times</option>
						</select>
					</div>
					
				</div>
				
				

			</form>
			<button class="w3-button w3-dark-grey" type="button" onclick="mySubmit()" form="search-form"><i class="fa fa-search w3-margin-right"></i> Search</button>
			<script type="text/javascript">
			function mySubmit(){
				var dep_air = document.getElementById("depart_airport").value;
				var arr_air = document.getElementById("arrival_airport").value;

				if (dep_air === arr_air || dep_air == "" || arr_air == "") {
					alert("Departure Airport and Arrival Airport shouldn't be the same!");
				}
				
				else{
					var dd = document.getElementById("depart-date").value;
					var rd = document.getElementById("return-date").value;

					if ($("#return-date").prop('disabled') == true) {
						document.getElementById("search-form").submit();
					}
					else if (rd > dd) {
						document.getElementById("search-form").submit();
					}
					else if (rd == dd) {
						if (confirm("Your departure date and return date is on the same day. \n Are you still want to search?") == true){
							document.getElementById("search-form").submit();
						}
					}
					else {
						alert("Your return date is earlier than departure date. \n Please recheck your information.");
					}
				}
			}
			</script>
	
	</div>
				
</body>
</html>