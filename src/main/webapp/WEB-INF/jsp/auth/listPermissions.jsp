<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Auth Permission</title>
</head>
<body>
<h1>Auth Permission</h1>
<h2>Create Permission</h2>
<form action="createPermission" method="post">
	Permission name: <input type="text" name="permissionName"/>
	<p/>
	<input type="submit" value="create"/>
</form>

<h2>Permission List</h2>
<table>
<tr><th>permission</th><th>action</th></tr>
<c:forEach items="${permissions}" var="permission">  
 	<tr>
 		<td>${permission.permissionName}</td>
 		<td><form action="deletePermission" method="post">
 			<input type="hidden" name="permissionName" value="${permission.permissionName}"/>
 			<input type="submit" value="delete"/>
 		</form></td>
 	</tr>
</c:forEach>
</table>

</body>
</html>