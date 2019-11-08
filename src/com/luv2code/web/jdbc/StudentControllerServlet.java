package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;

	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	// override init method inherited from generic Servlet class
	// init acts like a constructor
	@Override
	public void init() throws ServletException {
		super.init();

		try {
			// create our student db util & pass in the connection pool
			studentDbUtil = new StudentDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String command = request.getParameter("command");

			// route to the appropriate method

			// default to list command
			if (command == null) {
				command = "LIST";
			}

			switch (command) {

			case "LIST":
				// list the students ... in MVC fashion
				listStudents(request, response);
				break;

			case "ADD":
				addStudent(request, response);
				break;
				
			case "LOAD":
				loadStudent(request, response);
				break;
				
			case "UPDATE":
				updateStudent(request, response);
				break;
				
			case "DELETE":
				deleteStudent(request, response);
				break;

			default:
				listStudents(request, response);

			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student id from the hidden input in update-student-form.jsp 
		int id = Integer.parseInt(request.getParameter("studentId"));

		// perfomr update on database
		studentDbUtil.deleteStudent(id);
		
		// send the user back to the "list students" page
		listStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read student id from the hidden input in update-student-form.jsp 
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create a new student object
		Student student = new Student(id, firstName, lastName, email);
		
		// perfomr update on database
		studentDbUtil.updateStudent(student);
		
		// send the user back to the "list students" page
		listStudents(request, response);
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read student id from link param in list-students.jsp
		String studentId = request.getParameter("studentId");
		
		// get student form database (db util)
		Student student = studentDbUtil.getStudent(studentId);
		
		// place student in the request attribute
		request.setAttribute("student", student);
		
		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create new Student object
		Student student = new Student(firstName, lastName, email);
		
		// add the student to the database
		studentDbUtil.addStudent(student);
		
		// send back to main page (the student list)
		listStudents(request, response);

		
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get students from db util
		List<Student> students = studentDbUtil.getStudents();

		// add students to the request
		request.setAttribute("students", students);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);

	}

}
