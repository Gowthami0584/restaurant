package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.mydao;
import dto.Customer;

@WebServlet("/Signup")
@MultipartConfig
public class CustomerSignup extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");
		String email = req.getParameter("email");
		long phno = Long.parseLong(req.getParameter("phno"));
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		String gender = req.getParameter("gender");
		String pro = req.getParameter("country");
		int age = Period.between(dob, LocalDate.now()).getYears();
		
		Part pic = req.getPart("picture");
		byte[] picture = null;
		picture = new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);
		
		mydao dao = new mydao();
		
		//Logic to verify email and mobile number is not repeated
		if(dao.fetchByEmail(email)==null && dao.fetchByMobile(phno)==null) {
			Customer customer = new Customer();
			customer.setAge(age);
	     	customer.setCountry(pro);
	     	customer.setDob(dob);
	     	customer.setEmail(email);
	     	customer.setFullname(user);
	     	customer.setGender(gender);
	     	customer.setPassword(pass);
	     	customer.setPhonenumber(phno);
	     	customer.setPicture(picture);
	     	dao.save(customer);
			resp.getWriter().print("<h1 style='color:green'>Account Created Succefully</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		}
		
		
		else {
			resp.getWriter().print("<h1 style='color:red'>Email and Mobile should be unique</h1>");
			req.getRequestDispatcher("Signup.html").include(req, resp);
		}
		System.out.println(user + " "+pass+ " " + email + " " + phno + " " + dob + " " + pro + " " + gender + " " + picture);
		
		
	}
}
