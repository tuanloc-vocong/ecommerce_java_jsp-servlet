package com.ecommerce.controller;

import java.io.IOException;
import java.io.InputStream;

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
