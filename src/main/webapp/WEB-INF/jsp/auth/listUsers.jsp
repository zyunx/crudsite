<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User</title>
</head>
<body>
<h1>User</h1>
<h2>Create User</h2>
<form action="createUser" method="post">
	User name: <input type="text" name="userName"/>
	<p/>Password: <input type="password" name="password"/>
	<p/>
	<input type="submit" value="create"/>
</form>

<h2>User List</h2>
<table>
<tr><th>user name</th><th>action</th></tr>
<c:forEach items="${users}" var="user">
 	<tr>
 		<td>${user.userName}</td>
 		<td><a href="changeUserPassword?userName=${user.userName}">password</a>
 		| <form style="display:inline-block" action="deleteUser" method="post">
 			<input type="hidden" name="userName" value="${user.userName}"/>
 			<input type="submit" value="delete"/>
 		</form>
 		| <a href="listGroupsOfUser?userName=${user.userName}">groups</a>
 		| <a href="listPermissionsOfUser?userName=${user.userName}">permissions</a></td>
 	</tr>
</c:forEach>
</table>

</body>
</html>