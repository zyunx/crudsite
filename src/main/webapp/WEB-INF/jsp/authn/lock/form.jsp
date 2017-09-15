<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>${message}</p>
<p>To lock an account</p>
<form action="" method="post">
	User name: <input id="userNameInput" type="text" name="userName" value="${userName }"/>
	<input type="submit" value="lock"/>
</form>
</body>
</html>