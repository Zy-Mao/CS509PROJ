<%--
  Created by IntelliJ IDEA.
  User: mao
  Date: 21/04/2017
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">

<html>
<head>
	<title></title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script>
		var t;
		t = 5;
		function shua() {
			t = t - 1;
			document.getElementById("hints").innerHTML = "" + t + " Sec";
			if (t == 0) {
				document.location.reload();
			}
		}
	</script>
</head>
<body onload="window.setInterval(shua,1000);">
<p id="hints" style="color:blue">5 Sec</p>
</body>
</html>