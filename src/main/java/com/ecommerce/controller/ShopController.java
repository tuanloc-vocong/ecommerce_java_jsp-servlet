package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;

@WebServlet(name = "ShopController", value = "/shop")
public class ShopController extends HttpServlet {
    ProductDao productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String index = request.getParameter("index");
        if(index == null){
            index = "1";
        }

        List<Product> productList = productDao.get12ProductsOfPage(Integer.parseInt(index));
        List<Category> categoryList = categoryDao.getAllCategories();
        int totalProduct = productDao.getTotalNumberOfProducts();
        int totalPages = totalProduct / 12;

        if(totalProduct % 12 != 0){
            totalPages++;
        }

        String active = "active";

        request.setAttribute("product_list", productList);
        request.setAttribute("category_list", categoryList);
        request.setAttribute("total_pages", totalPages);
        request.setAttribute("shop_active", active);
        request.setAttribute("page_active", index);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("shop.jsp");
        requestDispatcher.forward(request, response);
    }
}
