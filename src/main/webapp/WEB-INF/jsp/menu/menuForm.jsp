<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>menu form</title>
</head>
<body>
<h1>Menu form </h1>
<form action="${form.action}" method="${form.method}">
	菜单名: <input type="text" name="itemName"/>
	<p/>菜单显示文本: <input type="text" name="itemText"/>
	<p/>菜单URL: <input type="text" name="itemUrl"/>
	<input type="hidden" name="itemParent" value="${form.menuItem.itemParent}"/>
	<input type="submit" value="create"/>
</form>
</body>
</html>