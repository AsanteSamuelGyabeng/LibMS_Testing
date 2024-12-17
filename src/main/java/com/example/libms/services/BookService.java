package com.example.libms.services;

import com.example.libms.model.Book;
import com.example.libms.util.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookService {



    /**
     * fetching a book from the database
     */
    public static ObservableList<Book> getAllBooks()  {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM books";
        try (Connection connection = DB.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    String title = resultSet.getString("title");
                    String genre = resultSet.getString("genre");
                    String isbn = resultSet.getString("isbn");
                    String isAvailable = resultSet.getString("is_available");
                    int copies = resultSet.getInt("copies");

                    bookList.add(new Book(bookId, title, genre, isbn, isAvailable, copies));
                }

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return bookList;
    }

    /**
     * Adding a book to the database
     */
    public boolean addBook(String title, String genre, String isbn, String isAvailable, int copies) throws ClassNotFoundException {
        String sql = "INSERT INTO books (title, genre, isbn, is_available, copies) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, genre);
            preparedStatement.setString(3, isbn);
            preparedStatement.setString(4, isAvailable);
            preparedStatement.setInt(5, copies);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    /**
     * @updateBook updates the book in the database
     */
    public static boolean updateBook(String title, String genre, String isbn, String isAvailable, int copies) throws ClassNotFoundException {
        String sql = "UPDATE books SET title = ?, genre = ?, is_available = ?, copies = ? WHERE isbn = ?";
        try (Connection connection = DB.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, genre);
                preparedStatement.setString(3, isAvailable);
                preparedStatement.setInt(4, copies);
                preparedStatement.setString(5, isbn);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }




    /**
     * @deleteBook - Deletes a book from the database
     */
    public static boolean deleteBook(String isbn) {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try (Connection connection = DB.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, isbn);  // Access the value using get()
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    /**
     * @searchBookByIsbn - Searches a book by isbn
     */
    public static Book searchBookByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (Connection connection = DB.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, isbn);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    String title = resultSet.getString("title");
                    String genre = resultSet.getString("genre");
                    String isAvailable = resultSet.getString("is_available");
                    int copies = resultSet.getInt("copies");

                    return new Book(bookId, title, genre, isbn, isAvailable, copies);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }



}
