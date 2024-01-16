package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.entity.CartProduct;

@WebServlet(name = "OrderDetailController", value = "/order-detail")
public class OrderDetailController extends HttpServlet {
    OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("order-id"));
        List<CartProduct> list = orderDao.getOrderDetailHistory(orderId);

        request.setAttribute("order_detail_list", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("order-detail.jsp");
        requestDispatcher.forward(request, response);
    }
}
