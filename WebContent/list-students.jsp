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
			<h2>FooBar University</h2>
		</div>
	</div>

	<div id="container">
		<div id="content">

			<input type="button" value="Add Student"
				onclick="window.location.href='add-student-form.jsp'; return false;"
				class="add-student-button" />

			<table>
				<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email</th>
						<th>Action</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="student" items="${students }">

						<!-- Set up update/delete link for each student -->
						<c:url var="updateLink" value="StudentControllerServlet">
							<c:param name="command" value="LOAD" />
							<c:param name="studentId" value="${student.id }" />
						</c:url>

						<c:url var="deleteLink" value="StudentControllerServlet">
							<c:param name="command" value="DELETE" />
							<c:param name="studentId" value="${student.id }" />
						</c:url>


						<tr>
							<td>${student.firstName }</td>
							<td>${student.lastName }</td>
							<td>${student.email }</td>
							<td><a href=${updateLink }>Update</a> | <a
								href=${deleteLink }
								onclick="if (!confirm('Are you sure you want to DELETE?')) return false">
									Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>