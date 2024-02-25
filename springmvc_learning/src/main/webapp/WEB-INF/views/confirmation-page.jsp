<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="springmvc.model.Reservation"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reservation Confirmation Page</title>
</head>
<body>
	<p>Your reservation is confirmed successfully. Please, re-check the
		details.</p>
	<%
	// Retrieve the reservation object from request attribute and cast it to Reservation
	Reservation reservation = (Reservation) request.getAttribute("reservation");
	%>

	First Name :
	<%=reservation.getFirstName()%>
	<br> Last Name :
	<%=reservation.getLastName()%>
</body>
</html>