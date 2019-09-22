package com.example.bookshelf.storage.impl;

import com.example.bookshelf.Book;
import com.example.bookshelf.Customer;
import com.example.bookshelf.storage.CustomersList;

import java.sql.*;

public class PostgresCustomerListImpl implements CustomersList {

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
    public void addCustomer(Customer customer) {
        final String sqlInsertBook = "INSERT INTO customers(" + "customer_id, name) " +
                "VALUES (?, ?);";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlInsertBook);

            preparedStatement.setLong(1, customer.getCustomerID());
            preparedStatement.setString(2, customer.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }
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
