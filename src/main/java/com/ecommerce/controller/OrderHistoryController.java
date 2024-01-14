package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Order;

@WebServlet(name = "OrderHistoryController", value = "/order-history")
public class OrderHistoryController extends HttpServlet {
    OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account)session.getAttribute("account");
        List<Order> orderList = orderDao.getOrderHistory(account.getId());

        request.setAttribute("order_list", orderList);
        request.setAttribute("order_history_active", "active");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("order-history.jsp");
        requestDispatcher.forward(request, response);
    }
}
