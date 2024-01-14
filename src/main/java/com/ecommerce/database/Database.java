package com.ecommerce.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public Connection getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_java_jsp_servlet?useSSL=true&requireSSL=true", "root", "");
            return connection;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Database().getConnection());
    }
}
