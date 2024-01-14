package com.ecommerce.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.dao.AccountDao;
import com.ecommerce.entity.Account;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    AccountDao accountDao = new AccountDao();

    private Account getAccountCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Account account;
        String username = "";
        String password = "";

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("username")){
                username = cookie.getValue();
            }

            if(cookie.getName().equals("password")){
                password = cookie.getValue();
            }
        }

        account = accountDao.checkLoginAccount(username, password);
        return account;
    }

    private void executeLogin(HttpServletRequest request, HttpServletResponse response, Account account) throws IOException {
        HttpSession session = request.getSession();
        boolean rememberMe = request.getParameter("remember-me-checkbox") != null;

        session.setAttribute("account", account);
        if(rememberMe){
            Cookie usernameCookie = new Cookie("username", account.getUsername());
            usernameCookie.setMaxAge(600);
            response.addCookie(usernameCookie);

            Cookie passwordCookie = new Cookie("password", account.getPassword());
            passwordCookie.setMaxAge(600);
            response.addCookie(passwordCookie);
        }

        response.sendRedirect("/");
    }

    private void checkLoginAccountFirstTime(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = "";
        if(request.getParameter("status") != null){
            status = request.getParameter("status");
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Account account = accountDao.checkLoginAccount(username, password);
        if(account == null && status.equals("typed")){
            String alert = "<div class=\"alert alert-danger wrap-input100\">\n" +
            "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
            "                            Wrong username or password!\n" +
            "                        </p>\n" +
            "                    </div>";

            request.setAttribute("alert", alert);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }else if(account == null){
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }else{
            executeLogin(request, response, account);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = getAccountCookie(request);
        if(account == null){
            checkLoginAccountFirstTime(request, response);
        }else{
            executeLogin(request, response, account);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }
}
