<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Message</title>
</head>
<body>
<h1>Message</h1>
<p>${message}</p>
<p>redirect after <span id="timeout">${timeout}</span> seconds.</p>
<script>
(function() {
	var timeout = ${timeout};
	setInterval(function() {
		console.log(timeout)
		timeout--;
		if (timeout == 0) {
			document.location = '${gotoUrl}';
			return;
		}
		document.getElementById('timeout').innerText = timeout;
	}, 1000);
})();
	
</script>
</body>
</html>