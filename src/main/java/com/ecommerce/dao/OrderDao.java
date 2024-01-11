package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.database.Database;
import com.ecommerce.entity.CartProduct;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;

public class OrderDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ProductDao productDao = new ProductDao();
    AccountDao accountDao = new AccountDao();

    public static void main(String[] args) {

    }

    public int getLastOrderId() {
        String query = "SELECT order_id FROM `order` ORDER BY order_id DESC LIMIT 1";
        int orderId = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return orderId;
    }

    private void createOrderDetail(List<CartProduct> cartProducts) {
        String query = "INSERT INTO order_detail (fk_order_id, fk_product_id, product_quantity, product_price) VALUES (?, ?, ?, ?)";
        int orderId = getLastOrderId();

        for (CartProduct cartProduct : cartProducts) {
            productDao.decreaseProductAmount(cartProduct.getProduct().getId(), cartProduct.getQuantity());
            try {
                Class.forName("com.mysql.jdbc.Driver");
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, cartProduct.getProduct().getId());
                preparedStatement.setInt(3, cartProduct.getQuantity());
                preparedStatement.setDouble(4, cartProduct.getPrice());
                preparedStatement.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Create order_detail catch:");
                System.out.println(e.getMessage());
            }
        }
    }

    public void createOrder(int accountId, double totalPrice, List<CartProduct> cartProducts) {
        connection = new Database().getConnection();
        String query = "INSERT INTO `order` (fk_account_id, order_total) VALUES (?, ?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setDouble(2, totalPrice);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Create order catch:");
            System.out.println(e.getMessage());
        }

        createOrderDetail(cartProducts);
    }

    public List<CartProduct> getSellerOrderDetail(int productId){
        List<CartProduct> list = new ArrayList<>();
        String query = "SELECT * FROM order_detail WHERE fk_product_id = " + productId;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Product product = productDao.getProduct(resultSet.getInt(1));
                int productQuantity = resultSet.getInt(3);
                double productPrice = resultSet.getDouble(4);

                list.add(new CartProduct(product, productQuantity, productPrice));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Query cart product list catch:");
            System.out.println(e.getMessage());
        }

        return list;
    }

    public List<Order> getOrderHistory(int accountId){
        List<Order> list = new ArrayList<>();
        String query = "SELECT * FROM `order` WHERE fk_account_id = " + accountId;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int orderId = resultSet.getInt(1);
                double orderTotal = resultSet.getDouble(3);
                Date orderDate = resultSet.getDate(4);

                list.add(new Order(orderId, orderTotal, orderDate));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Order history catch:");
            System.out.println(e.getMessage());
        }

        return list;
    }

    public List<CartProduct> getOrderDetailHistory(int orderId){
        List<CartProduct> list = new ArrayList<>();
        String query = "SELECT * FROM order_detail WHERE fk_order_id = " + orderId;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = productDao.getProduct(resultSet.getInt(1));
                int quantity = resultSet.getInt(3);
                double price = resultSet.getDouble(4);

                list.add(new CartProduct(product, quantity, price));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Get order detail catch:");
            System.out.println(e.getMessage());
        }

        return list;
    }
}
