package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Product;

@WebServlet(name = "ProductDetailController", value = "/product-detail")
public class ProductDetailController extends HttpServlet {
    ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean alert = request.getHeader("invalid-quantity") != null;
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDao.getProduct(id);

        String disabled = "";
        if(product.getAmount() <= 0){
            disabled = "disabled";
        }

        List<Product> productList = productDao.getAllProducts();
        
    }
}
