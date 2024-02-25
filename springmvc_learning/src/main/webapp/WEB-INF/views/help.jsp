<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Help Page</title>
</head>
<body>
	<%
	String name = (String) request.getAttribute("name");
	Integer roll = (Integer) request.getAttribute("roll");
	LocalDateTime time = (LocalDateTime) request.getAttribute("time");
	%>

	<h1>
		Hello my name is
		<%=name%>
	</h1>
	<h1>
		My roll number is
		<%=roll%>
	</h1>
	<h1>
		Date and time is
		<%=time%>
	</h1>
</body>
</html>