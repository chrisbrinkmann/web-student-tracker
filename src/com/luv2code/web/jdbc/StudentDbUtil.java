package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	private DataSource dataSource;

	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<Student>();

		// initialize our JDBC objects
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection from the pool
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select * from student order by last_name";

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");

				// create new student object
				Student student = new Student(id, firstName, lastName, email);

				// add it to the list of students
				students.add(student);
			}
			return students;

		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close(); // returns the connection to the pool
			}

		} catch (Exception e) {
			System.out.println("There was a problem closing the JDBC resources.");
		}

	}

	public void addStudent(Student student) throws Exception {

		// initialize our JDBC objects
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get a connection from the pool
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "insert into student " + "(first_name, last_name, email) " + "values (?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set the param values (?) for the student
			myStmt.setString(1, student.getFirstName());
			myStmt.setString(2, student.getLastName());
			myStmt.setString(3, student.getEmail());

			// execute query
			myStmt.executeQuery(sql);

		} finally {
			// close JDBC objects
			close(myConn, myStmt, null);
		}

	}
}
