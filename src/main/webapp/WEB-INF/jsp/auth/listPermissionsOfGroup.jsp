<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Auth Group</title>
</head>
<body>
<h1>Auth Group</h1>
<h2>Group's permissions</h2>
<table>
<tr><th>permission name</th><th>action</th></tr>
<c:forEach items="${groupPermissions}" var="gp">  
 	<tr>
 		<td>${gp.permissionName}</td>
 		<td>
 			<form action="revokePermissionFromGroup" method="post">
 				<input type="hidden" name="groupName" value="${gp.groupName}"/>
 				<input type="hidden" name="permissionName" value="${gp.permissionName}"/>
 				<input type="submit" value="revoke"/>
 			</form>
 		</td>
 	</tr>
</c:forEach>
</table>
<h2>Grant permission to group</h2>
<form action="grantPermissionToGroup" method="post">
	permission name: <input type="text" name="permissionName"/>
	<p/>
	<input type="hidden" name="groupName" value="${groupName}"/>
	<input type="submit" value="grant"/>
</form>
</body>
</html>