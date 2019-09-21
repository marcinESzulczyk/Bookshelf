package com.example.bookshelf.storage.impl;

import com.example.bookshelf.Book;
import com.example.bookshelf.storage.BookStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresBookStorageImpl implements BookStorage {
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
    public Book getBook(long id) {
        final String sqlSelectBook = "SELECT * FROM books WHERE book_id = ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectBook);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getLong("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPagesSum(resultSet.getInt("page_sum"));
                book.setYearOfPublished(resultSet.getInt("year_of_published"));
                book.setPublishingHouse(resultSet.getString("publishing_house"));

                return book;
            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw  new  RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        final String sqlselectAllBooks = "SELECT * FROM books;";

        Connection connection = initializeDataBaseConnection();
        Statement statement = null;

        List<Book> books = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlselectAllBooks);

            while (resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getLong("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPagesSum(resultSet.getInt("page_sum"));
                book.setYearOfPublished(resultSet.getInt("year_of_published"));
                book.setPublishingHouse(resultSet.getString("publishing_house"));

                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw  new  RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, statement);
        }
        return books;
    }

    @Override
    public void addBook(Book book) {
        final String sqlInsertBook = "INSERT INTO books(" + "book_id, title, author, page_sum, year_of_published, publishing_house) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlInsertBook);

            preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setInt(4, book.getPagesSum());
            preparedStatement.setInt(5, book.getYearOfPublished());
            preparedStatement.setString(6, book.getPublishingHouse());

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
    @Override
    public List<Book> getBookStorage() {
        return null;
    }
}










   /* private static Statement prepareStatement () throws ClassNotFoundException, SQLException{

            Class.forName("org.postresql.Driver");

            Connection connection = DriverManager.getConnection(JDBC_URL,DATABASE_USER,DATABASE_PASS);

            Statement statement = connection.createStatement();

            return statement;

    }

    public void closeDB (Statement statement, Connection connection)throws SQLException{
        statement.close();
        connection.close();

    }
*/

    //private static ResultSet bookStorage = new ArrayList<Book>();

   /* public Book getBook(long id) throws ClassNotFoundException, SQLException{
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException c){
            System.out.println(c.getException());
        }


        Connection connection = DriverManager.getConnection(JDBC_URL,DATABASE_USER,DATABASE_PASS);

        Statement statement = connection.createStatement();

    ResultSet bookStorage = statement.executeQuery("SELECT * FROM books2;");

        while (bookStorage.next()){

         int bookID = bookStorage.getInt(1);
            if (bookID == id){
                Book book = new Book(id, bookStorage.getString(2),bookStorage.getString(3),bookStorage.getInt(4),bookStorage.getInt(5),bookStorage.getString(6));
                System.out.println(bookStorage.getString(2));
                statement.close();
                connection.close();
                return book;

                }

            }
        statement.close();
        connection.close();
        return null;
    }

    public List<Book> getAllBooks()throws ClassNotFoundException, SQLException{

        Class.forName("org.postgresql.Driver");

        Connection connection = DriverManager.getConnection(JDBC_URL,DATABASE_USER,DATABASE_PASS);

        Statement statement = connection.createStatement();

        ResultSet bookStorage2 = statement.executeQuery("SELECT * FROM books2;");
        List<Book> bookStorage = new ArrayList<Book>();

        while (bookStorage2.next()){
            bookStorage.add(new Book(bookStorage2.getLong(1), bookStorage2.getString(2),bookStorage2.getString(3),bookStorage2.getInt(4),bookStorage2.getInt(5),bookStorage2.getString(6)));
        }
        statement.close();
        connection.close();
        return bookStorage;

    }

    public void addBook (Book book){
        List<Book> bookStorage = new ArrayList<Book>();
        bookStorage.add(book);
    }
}*/
