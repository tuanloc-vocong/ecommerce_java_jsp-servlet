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

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;

@WebServlet(name = "ProductManagementController", value = "/product-management")
public class ProductManagementController extends HttpServlet {
    ProductDao productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account)session.getAttribute("account");
        int sellerId = account.getId();
        List<Product> productList = productDao.getSellerProducts(sellerId);
        List<Category> categoryList = categoryDao.getAllCategories();

        request.setAttribute("category_list", categoryList);
        request.setAttribute("product_list", productList);
        request.setAttribute("product_management_active", "active");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product-management.jsp");
        requestDispatcher.forward(request, response);
    }
}
