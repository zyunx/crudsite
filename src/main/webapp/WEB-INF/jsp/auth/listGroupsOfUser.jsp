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
<h2>User's groups</h2>
<table>
<tr><th>group name</th><th>action</th></tr>
<c:forEach items="${groups}" var="g">  
 	<tr>
 		<td>${g.groupName}</td>
 		<td>
 			<form action="userLeaveGroup" method="post">
 				<input type="hidden" name="userName" value="${userName}"/>
 				<input type="hidden" name="groupName" value="${g.groupName}"/>
 				<input type="submit" value="delete"/>
 			</form>
 		</td>
 	</tr>
</c:forEach>
</table>
<h2>Join group</h2>
<form action="userJoinGroup" method="post">
	Group name: <input type="text" name="groupName"/>
	<p/>
	<input type="hidden" name="userName" value="${userName}"/>
	<input type="submit" value="join"/>
</form>
</body>
</html>