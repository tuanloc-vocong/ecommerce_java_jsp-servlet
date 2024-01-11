package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.database.Database;
import com.ecommerce.entity.Category;

public class CategoryDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private void queryCategoryProductAmount(Category category){
        int productId = category.getId();
        String query = "SELECT COUNT(*) FROM product WHERE fk_category_id = " + productId + " AND product_is_deleted = false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                category.setTotalCategoryProduct(resultSet.getInt(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Get category products amount catch: ");
            System.out.println(e.getMessage());
        }
    }

    public Category getCategory(int categoryId){
        Category category = new Category();
        String query = "SELECT * FROM category WHERE category_id = " + categoryId;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        queryCategoryProductAmount(category);
        return category;
    }

    public List<Category> getAllCategories(){
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM category";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
                list.add(category);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        for(Category category : list){
            queryCategoryProductAmount(category);
        }

        return list;
    }
}
