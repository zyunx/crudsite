<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Form</title>
</head>
<body>
<h1>Login Form</h1>
<form action="login" method="post">
	User name: <input id="userNameInput" type="text" name="userName" />
	<p/>Password: <input type="password" name="password" />
	<p/><input type="submit" value="login"/>
</form>
<script>
(function() {
	document.getElementById("userNameInput").focus();	
})();
</script>
</body>
</html>