package com.ducvu.bookstore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BookDAO.java
 * This DAO class provides the CRUD operations (create, read, update, delete)
 * for the table book in database
 * This is the data access layer
 * @author ducvu
 */
public class BookDAO {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public BookDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null || !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public List<Book> listAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String selectSQL = "SELECT * FROM book";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet result = statement.executeQuery(selectSQL);
        while (result.next()) {
            int id = result.getInt("book_id");
            String title = result.getString("title");
            String author = result.getString("author");
            double price = result.getDouble("price");
            books.add(new Book(id, title, author, price));
        }
        result.close();
        statement.close();
        disconnect();

        return books;
    }

    public boolean insertNewBook(Book book) throws SQLException {
        String insertSQL = "INSERT INTO book" + " (title, author, price) VALUES (?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(insertSQL);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setDouble(3, book.getPrice());

        // executeUpdate() returns the number of rows affected by the query
        boolean isInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();

        return isInserted;
    }

    public Book getBookById(int book_id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM book" + " WHERE book_id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, book_id);

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            String title = result.getString("title");
            String author = result.getString("author");
            double price = result.getDouble("price");

            book = new Book(book_id, title, author, price);
        }
        result.close();
        statement.close();

        return book;
    }

    public boolean updateBook(Book book) throws SQLException{
        String updateSQL = "UPDATE book" + " SET title = ?, author = ?, price = ?" + " WHERE book_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(updateSQL);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setDouble(3, book.getPrice());
        statement.setInt(4, book.getBook_id());

        boolean isUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();

        return isUpdated;
    }

    public boolean deleteBook(int book_id) throws SQLException {
        String deleteSQL = "DELETE FROM book WHERE book_id = ?";

        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(deleteSQL);
        statement.setInt(1, book_id);

        boolean isDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();

        return isDeleted;
    }
}

