<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Auth User</title>
</head>
<body>
<h1>Auth User</h1>
<h2>User List</h2>
<table>
<tr><th>user name</th><th>action</th></tr>
<c:forEach items="${users}" var="user">  
 	<tr>
 		<td>${user.userName}</td>
 		<td><a href="user?userName=${user.userName}">edit</a>
 		| <a href="user/delete?userName=${user.userName}">delete</a>
 		| <a href="listGroupsOfUser?userName=${user.userName}">groups</a></td>
 	</tr>
</c:forEach>
</table>
<h2>Create User</h2>
<form action="user/create" method="post">
	User name: <input type="text" name="userName"/>
	<p/>Password: <input type="password" name="password"/>
	<p/>
	<input type="submit" value="create"/>
</form>
</body>
</html>