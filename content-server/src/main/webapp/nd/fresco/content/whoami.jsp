<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>whoami</title>
</head>
<body>
	<h1>
		Hello
		<%=request.getRemoteUser()%></h1>
</body>
</html>