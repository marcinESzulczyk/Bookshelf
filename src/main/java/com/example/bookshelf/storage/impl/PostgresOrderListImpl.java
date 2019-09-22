package com.example.bookshelf.storage.impl;

import com.example.bookshelf.Book;
import com.example.bookshelf.Customer;
import com.example.bookshelf.Order;
import com.example.bookshelf.OrderItems;
import com.example.bookshelf.storage.OrderList;

import java.sql.*;

public class PostgresOrderListImpl implements OrderList {

    private static String JDBC_URL = "jdbc:postgresql://localhost:5432/booksdb";
    private static String DATABASE_USER = "postgres";
    private static String DATABASE_PASS = "marcinszu";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Server can't find postgresql Driver class: \n" + e);
        }
    }

    private Connection initializeDataBaseConnection(){
        try {
            return DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);
        } catch (SQLException e) {
            System.err.println("Server can't initialize database connection: \n" + e);
            throw new RuntimeException("Server can't initialize database connection");
        }
    }

    @Override
    public void addOrder(OrderItems orderItems) {
        final String sqlInsertOrder = "INSERT INTO orders(" + "order_id, order_date, customer_id) " +
                "VALUES (?, ?, ?);";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        long a = orderItems.getOrgerItemID()*10;

        try {
            preparedStatement = connection.prepareStatement(sqlInsertOrder);

            preparedStatement.setLong(1, orderItems.getOrgerItemID());
            preparedStatement.setDate(2, orderItems.getOrder().getOrderDate());
            preparedStatement.setLong(3,orderItems.getOrder().getCustomerID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }

        final String sqlInsertOrder2 = "INSERT INTO order_items(" + "order_item_id, order_id, book_id, amount) " + "VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement1 = null;

        for(Book book:orderItems.getBooks()){

            try {
                preparedStatement1 = connection.prepareStatement(sqlInsertOrder2);

                preparedStatement1.setLong(1,a);
                preparedStatement1.setLong(2,orderItems.getOrgerItemID());
                preparedStatement1.setLong(3,book.getId());
                preparedStatement1.setInt(4,book.getAmount());
                preparedStatement1.executeUpdate();
            }catch (SQLException e) {
                System.err.println("Error during invoke SQL query: \n" + e.getMessage());
                throw new RuntimeException("Error during invoke SQL query");
            }
            a=a+1;
        }

            closeDataBaseResources(connection, preparedStatement);

    }

    private void closeDataBaseResources(Connection connection, Statement Statement) {
        try {
            if (Statement != null) {
                Statement.close();
            }
            if (connection != null){
                connection.close();
            }
        }catch (SQLException e){
            System.err.println("Error during closing database resources: \n" + e);
            throw new RuntimeException("Error during closing database resources");
        }
    }
}
