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

	public Student getStudent(String studentId) throws Exception {
		Student student = null;
		int id = Integer.parseInt(studentId);

		// initialize our JDBC objects
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			// get connection to db
			myConn = dataSource.getConnection();

			// create sql to get selected student
			String sql = "select * from student where id=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id);

			// execute statement
			myRs = myStmt.executeQuery();

			// retreive data from result set row
			if (myRs.next()) {
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");

				// create a new student object
				// use the studentId during construction
				student = new Student(id, firstName, lastName, email);
			} else {
				throw new Exception("Could not find student with id: " + id);
			}

			return student;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, null);
		}

	}

	public void updateStudent(Student student) throws Exception {
		// initialize our JDBC objects
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get a connection from the pool
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "update student " + "set first_name = ?, last_name = ?, email = ? " + "where id = ?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set the param values (?) for the student
			myStmt.setString(1, student.getFirstName());
			myStmt.setString(2, student.getLastName());
			myStmt.setString(3, student.getEmail());
			myStmt.setInt(4, student.getId());

			// execute query
			myStmt.execute();

		} finally {
			// close JDBC objects
			close(myConn, myStmt, null);
		}

	}

	public void deleteStudent(int id) throws Exception {
	// initialize our JDBC objects
			Connection myConn = null;
			PreparedStatement myStmt = null;

			try {
				// get a connection from the pool
				myConn = dataSource.getConnection();

				// create sql statement
				String sql = "delete from student where id = ?";

				// prepare statement
				myStmt = myConn.prepareStatement(sql);

				// set the param values (?) for the student
				myStmt.setInt(1, id);

				// execute query
				myStmt.execute();

			} finally {
				// close JDBC objects
				close(myConn, myStmt, null);
			}
	}
}
