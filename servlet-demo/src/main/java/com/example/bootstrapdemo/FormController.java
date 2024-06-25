package com.example.bootstrapdemo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="formController", value="/form")
public class FormController extends HttpServlet {
    public void init() {}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        if(email == null | password == null) {
            response.sendRedirect("Error.jsp");
        }
        response.sendRedirect("demo.jsp");

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        System.out.println("here is contextPath" + request.getContextPath());
        if(email == null | password == null) {
          response.sendRedirect("Error.jsp");
        }
        response.sendRedirect("demo.jsp");
    }
    public void destroy() {

    }
}
