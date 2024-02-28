<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.List"%>
<%@ page import="springmvc.model.Emp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Employees List</h1>
	<table border="2" width="70%" cellpadding="2">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Salary</th>
			<th>Designation</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%
		List<Emp> employees = (List<Emp>) request.getAttribute("list");
		for (Emp emp : employees) {
		%>
		<tr>
			<td><%=emp.getId()%></td>
			<td><%=emp.getName()%></td>
			<td><%=emp.getSalary()%></td>
			<td><%=emp.getDesignation()%></td>
			<td><a href="editemp/<%=emp.getId()%>">Edit</a></td>
			<td><a href="deleteemp/<%=emp.getId()%>">Delete</a></td>
		</tr>
		<%
		}
		%>
	</table>
	<br />
	<a href="empform">Add New Employee</a>

</body>
</html>