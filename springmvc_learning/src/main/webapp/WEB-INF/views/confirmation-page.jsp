<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="springmvc.model.Reservation"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
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
	//Reservation reservation = (Reservation) request.getAttribute("reservation");
	%>
<%--
	First Name :
	<%=//reservation.getFirstName()%>
	<br> Last Name :
	<%=//reservation.getLastName()%>
	<br> Gender :
	<%=//reservation.getGender()%>
	<br> Meals:
	<%
	//for (String s : reservation.getFood()) {
	%>

	<h1><%=//s%></h1>
	<%
	//}
	%>
	Leaving From :
	<%=
	//reservation.getCityFrom()
	%>
	 Going To :
<%= reservation.getCityTo() %> --%>


	<hr>

	<p>Your reservation is confirmed successfully. Please, re-check the
		details.</p>
	First Name : ${reservation.firstName}
	<br> Last Name : ${reservation.lastName}
	<br> Gender: ${reservation.gender}
	<br> Meals:
	<ul>
		<c:forEach var="meal" items="${reservation.food}">
			<li>${meal}</li>
		</c:forEach>
	</ul>
	Leaving From : ${reservation.cityFrom}
	<br> Going To : ${reservation.cityTo}

</body>
</html>