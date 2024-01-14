package com.ecommerce.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Account;

@WebServlet(name = "AddProductController", value = "/add-product")
@MultipartConfig
public class AddProductController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String productName = request.getParameter("product-name");
        double productPrice = Double.parseDouble(request.getParameter("product-price"));
        String productDescription = request.getParameter("product-description");
        int productCategory = Integer.parseInt(request.getParameter("product-category"));
        int productAmount = Integer.parseInt(request.getParameter("product-amount"));

        Part part = request.getPart("product-image");
        InputStream inputStream = part.getInputStream();

        HttpSession session = request.getSession();
        Account account = (Account)session.getAttribute("account");
        int sellerId = account.getId();

        ProductDao productDao = new ProductDao();
        productDao.addProduct(productName, inputStream, productPrice, productDescription, productCategory, sellerId, productAmount);
        response.sendRedirect("product-management");
    }
}
