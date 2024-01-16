package com.ecommerce.controller;

import java.io.IOException;
import java.io.InputStream;

import com.ecommerce.dao.AccountDao;

@WebServlet(name = "RegisterController", value = "/register")
@MultipartConfig
public class RegisterController extends HttpServlet {
    AccountDao accountDao = new AccountDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeat-password");

        Part part = request.getPart("profile-image");
        InputStream inputStream = part.getInputStream();

        if(!password.equals(repeatPassword)){
            String alert = "<div class=\"alert alert-danger wrap-input100\">\n" +
            "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
            "                            Incorrect password!\n" +
            "                        </p>\n" +
            "                    </div>";
            request.setAttribute("alert", alert);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }else if(accountDao.checkUsernameExists(userName)){
            String alert = "<div class=\"alert alert-danger wrap-input100\">\n" +
            "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
            "                            Username already exist!\n" +
            "                        </p>\n" +
            "                    </div>";
            request.setAttribute("alert", alert);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }else{
            accountDao.createAccount(userName, repeatPassword, inputStream);
            String alert = "<div class=\"alert alert-success wrap-input100\">\n" +
            "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
            "                            Create account successfully!\n" +
            "                        </p>\n" +
            "                    </div>";
            request.setAttribute("alert", alert);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
