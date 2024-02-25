<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


	<%
	String message = (String) request.getAttribute("message");
	List<String> friends = (List<String>) request.getAttribute("f");
	%>

	<%=message%>

	<%
	for (String s : friends) {
	%>

	<h1><%=s%></h1>

	<%
	}
	%>

</body>
</html>