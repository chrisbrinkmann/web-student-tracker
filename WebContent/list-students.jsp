<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<link type="text/css" rel="stylesheet" href="css/style.css">

<meta charset="ISO-8859-1">
<title>Student Tracker</title>
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>User List</h2>
		</div>
	</div>

	<div id="container">
		<div id="content">
		
			<input type="button" value="Add User"
				onclick="window.location.href='add-student-form.jsp'; return false;"
				class="add-student-button" />
		
			<table>
				<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="student" items="${students }">
						<tr>
							<td>${student.firstName }</td>
							<td>${student.lastName }</td>
							<td>${student.email }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>