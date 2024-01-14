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
import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.Product;

@WebServlet(name = "OrderManagementController", value = "/order-management")
public class OrderManagementController extends HttpServlet {
    ProductDao productDao = new ProductDao();
    OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account)session.getAttribute("account");
        int accountId = account.getId();

        List<Product> productList = productDao.getSellerProducts(accountId);
        List<CartProduct> cartProductList = null;
        for(Product product : productList){
            if(cartProductList == null){
                cartProductList = orderDao.getSellerOrderDetail(product.getId());
            }else{
                cartProductList.addAll(orderDao.getSellerOrderDetail(product.getId()));
            }
        }

        request.setAttribute("order_detail_list", cartProductList);
        request.setAttribute("order_management_active", "active");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("order-management.jsp");
        requestDispatcher.forward(request, response);
    }
}
