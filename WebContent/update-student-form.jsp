
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Student</title>

<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">

</head>
<body>

	<div id="wrapper">
		<div id="header">
			<h2>FooBar University</h2>
		</div>
	</div>

	<div id="container">
		<h3>Update Student</h3>

		<form action="StudentControllerServlet" method="GET">

			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="studentId" value="${student.id }" />

			<table>
				<tbody>
					<tr>
						<td><input type="text" name="firstName"
							value="${student.firstName }" /></td>
					</tr>
					<tr>
						<td><input type="text" name="lastName"
							value="${student.lastName }" /></td>
					</tr>
					<tr>
						<td><input type="text" name="email"
							value="${student.email }" /></td>
					</tr>
										<tr>
						<td><input type="submit" value="save"
							class="save" /></td>
					</tr>

				</tbody>
			</table>

		</form>
		
		<div style="clear: both;"></div>

		<p><a href="StudentControllerServlet">Back to Student List</a></p>

	</div>

</body>
</html>