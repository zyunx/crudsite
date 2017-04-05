<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Permission</title>
</head>
<body>
<h1>User Permissions</h1>

<h2>Grant permission to user</h2>
<form action="grantPermissionToUser" method="post">
	permission name: <input type="text" name="permissionName"/>
	<p/>
	<input type="hidden" name="userName" value="${userName}"/>
	<input type="submit" value="grant"/>
</form>

<h2>User's own permissions</h2>
<table>
<tr><th>permission name</th><th>action</th></tr>
<c:forEach items="${userPermissionsOfHimself}" var="uph">  
 	<tr>
 		<td>${uph.permissionName}</td>
 		<td>
 			<form action="revokePermissionFromUser" method="post">
 				<input type="hidden" name="userName" value="${uph.userName}"/>
 				<input type="hidden" name="permissionName" value="${uph.permissionName}"/>
 				<input type="submit" value="revoke"/>
 			</form>
 		</td>
 	</tr>
</c:forEach>
</table>

<h2>Permissions of user's group</h2>
<table>
<tr><th>permission name</th></tr>
<c:forEach items="${userPermissionsOfGroups}" var="upg">  
 	<tr>
 		<td>${upg.permissionName}</td>
 	</tr>
</c:forEach>
</table>

</body>
</html>