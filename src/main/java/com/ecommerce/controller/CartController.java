package com.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;

@WebServlet(name = "CartController", value = "/cart")
public class CartController extends HttpServlet {
    ProductDao productDao = new ProductDao();

    private void removeCartProduct(int productId, Order order, double totalPrice){
        List<CartProduct> list = order.getCartProducts();
        for(Iterator<CartProduct> iterator = list.iterator(); iterator.hasNext();){
            CartProduct cartProduct = iterator.next();
            if(cartProduct.getProduct().getId() == productId){
                totalPrice -= (cartProduct.getPrice() * cartProduct.getQuantity());
                iterator.remove();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(request.getParameter("remove-product-id") != null){
            Order order = (Order)session.getAttribute("order");
            double totalPrice = (double)session.getAttribute("total_price");
            int productId = Integer.parseInt(request.getParameter("remove-product-id"));
            removeCartProduct(productId, order, totalPrice);
            response.sendRedirect("cart.jsp");
            return;
        }

        int quantity = 1;
        int productId;
        double totalPrice;

        if(session.getAttribute("total_price") == null){
            totalPrice = 0;
        }else{
            totalPrice = (double)session.getAttribute("total_price");
        }

        if(request.getParameter("product-id") != null){
            productId = Integer.parseInt(request.getParameter("product-id"));
            Product product = productDao.getProduct(productId);

            if(product != null){
                if(request.getParameter("quantity") != null){
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                    if(product.getAmount() - quantity < 0){
                        response.sendRedirect("product-detail?id=" + product.getId() + "&invalid-quantity=1");
                        return;
                    }
                }

                if(session.getAttribute("order") == null){
                    Order order = new Order();
                    List<CartProduct> list = new ArrayList<>();

                    CartProduct cartProduct = new CartProduct();
                    cartProduct.setQuantity(quantity);
                    cartProduct.setProduct(product);
                    cartProduct.setPrice(product.getPrice());

                    totalPrice += product.getPrice() * quantity;
                    list.add(cartProduct);
                    order.setCartProducts(list);

                    session.setAttribute("total_price", totalPrice);
                    session.setAttribute("order", order);
                }else{
                    Order order = (Order)session.getAttribute("order");
                    List<CartProduct> list = order.getCartProducts();
                    boolean flag = false;

                    for(CartProduct cartProduct : list){
                        if(cartProduct.getProduct().getId() == product.getId()){
                            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
                            totalPrice += product.getPrice() * quantity;
                            flag = true;
                        }
                    }

                    if(!flag){
                        CartProduct cartProduct = new CartProduct();
                        cartProduct.setQuantity(quantity);
                        cartProduct.setProduct(product);
                        cartProduct.setPrice(product.getPrice());
                        totalPrice += product.getPrice() * quantity;
                        list.add(cartProduct);
                    }

                    session.setAttribute("total_price", totalPrice);
                    session.setAttribute("order", order);
                }
            }
            response.sendRedirect("product-detail?id=" + productId);
        }
    }
}
