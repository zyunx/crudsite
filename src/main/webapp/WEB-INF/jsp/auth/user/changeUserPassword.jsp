<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Change ${userName}'s password</title>
</head>
<body>
	<h1>User</h1>
	<h2>Change ${userName}'s password</h2>
	<form method="post">
		<p/>Password: <input type="password" name="password" />
		<p/><input type="submit" value="change"/>
	</form>
</body>
</html>