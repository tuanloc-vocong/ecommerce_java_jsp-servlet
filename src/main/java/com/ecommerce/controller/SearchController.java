package com.ecommerce.controller;

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.ProductDao;

@WebServlet(name = "SearchController", value = "/search")
public class SearchController extends HttpServlet {
    ProductDao productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServ)
}
