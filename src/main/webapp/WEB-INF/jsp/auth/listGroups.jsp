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
<h1>Auth Group</h1>
<h2>Create Group</h2>
<form action="createGroup" method="post">
	Group name: <input type="text" name="groupName"/>
	<p/>
	<input type="submit" value="create"/>
</form>

<h2>Group List</h2>
<table>
<tr><th>group name</th><th>action</th></tr>
<c:forEach items="${groups}" var="g">  
 	<tr>
 		<td>${g.groupName}</td>
 		<td><form style="display:inline-block" action="deleteGroup" method="post">
 			<input type="hidden" name="groupName" value="${g.groupName}"/>
 			<input type="submit" value="delete"/>
 		</form>
 		| <a href="listPermissionsOfGroup?groupName=${g.groupName}">权限</a></td>
 	</tr>
</c:forEach>
</table>
</body>
</html>