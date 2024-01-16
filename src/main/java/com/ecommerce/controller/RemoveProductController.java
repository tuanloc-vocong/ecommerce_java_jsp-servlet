package com.ecommerce.controller;

import java.io.IOException;
import java.rmi.ServerException;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Product;

@WebServlet(name = "RemoveProductController", value = "/remove-product")
public class RemoveProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
        int productId = Integer.parseInt(request.getParameter("product-id"));
        ProductDao productDao = new ProductDao();
        Product product = productDao.getProduct(productId);
        productDao.removeProduct(product);

        response.sendRedirect("product-management");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
