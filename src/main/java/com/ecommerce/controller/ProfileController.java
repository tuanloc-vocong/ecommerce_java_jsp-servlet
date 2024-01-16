package com.ecommerce.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.dao.AccountDao;
import com.ecommerce.entity.Account;

@WebServlet(name = "ProfileController", value = "/profile-page")
@MultipartConfig
public class ProfileController extends HttpServlet {
	AccountDao accountDao = new AccountDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("profile-page.jsp");
		requestDispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account account = (Account)session.getAttribute("account");

		int accountId = account.getId();
		String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		Part part = request.getPart("profile-image");
		InputStream inputStream = part.getInputStream();

		accountDao.editProfileInformation(accountId, firstName, lastName, address, email, phone, inputStream);
		response.sendRedirect("login");
	}
}
