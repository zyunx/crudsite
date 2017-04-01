<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>users</title>
</head>
<body>
	<h1>Auth User</h1>
	<h2>Update user</h2>
	<c:choose>
		<c:when test="${empty user}">
   User does not exist.
   		</c:when>
		<c:otherwise>
			<form action="user/update" method="post">
				User name: <input type="text" name="userName" value="${user.userName}" />
				<p/>Password: <input type="password" name="password" />
				<p/><input type="submit" value="update"/>
			</form>
		</c:otherwise>
	</c:choose>

</body>
</html>