<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	String message = (String) request.getAttribute("message");
	%>

	<%=message%>
	<br>
	<br>
	<jsp:include page="/WEB-INF/views/basicForm.jsp"></jsp:include>
</body>
</html>