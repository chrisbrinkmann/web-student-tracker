<%@ page import="java.util.*, com.luv2code.web.jdbc.*"%>

<!DOCTYPE html>
<html>
<head>

<link type="text/css" rel="stylesheet" href="css/style.css">

<meta charset="ISO-8859-1">
<title>Student Tracker</title>
</head>

<%
	// get the students form the request object (sent by servlet)
	List<Student> students = (List<Student>) request.getAttribute("students");
%>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>User List</h2>
		</div>
	</div>

	<div id="container">
		<div id="content">
			<table>
				<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email</th>
					</tr>
				</thead>

				<tbody>
					<%
						for (Student student : students) {
					%>
					<tr>
						<td><%=student.getFirstName()%></td>
						<td><%=student.getLastName()%></td>
						<td><%=student.getEmail()%></td>
					</tr>
					<%
						}
					%>
				</tbody>

			</table>
		</div>
	</div>

</body>
</html>