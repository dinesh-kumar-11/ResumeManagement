package org.dinesh.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ActionServlet
 */

public class ajaxServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;

    
    public ajaxServlet() {
        // TODO Auto-generated constructor stub
    }


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String name = null;
	name = "<b>Hello " + request.getParameter("user") + "</b>";
	if (request.getParameter("user").toString().equals("")) {
	    name = "<b>Hello User</b>";
	}
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(name); 
 }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }

}
