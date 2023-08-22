package dao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Customer;

public class MyLogin extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
		
		//verify if email exists
		mydao dao = new mydao();
		Customer customer = dao.fetchByEmail(email);
		if(customer == null) {
			resp.getWriter().print("<h1 style='color:red'>invalid email</h1>");
		}
		else {
			if(pass.equals(customer.getPassword())) {
				resp.getWriter().print("<h1 style='color:green'>Login Success</h1>");
			}
			else {
				resp.getWriter().print("<h1 style = 'color:red'>invalid password</h1>");
			}
		}
		
	}
	

}
