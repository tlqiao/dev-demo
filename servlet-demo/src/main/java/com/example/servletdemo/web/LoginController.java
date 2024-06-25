package com.example.servletdemo.web;

import com.example.servletdemo.dao.LoginDao;
import com.example.servletdemo.model.LoginBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @email Ramesh Fadatare
 */

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginDao loginDao;
	public void init() {
		loginDao = new LoginDao();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.sendRedirect("login/login.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		authenticate(request, response);
	}
	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername(username);
		loginBean.setPassword(password);

		try {
			if (loginDao.validate(loginBean)) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-list.jsp");
				dispatcher.forward(request, response);
			} else {
				HttpSession session = request.getSession();
				 session.setAttribute("user", username);
				 response.sendRedirect("login.jsp");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
