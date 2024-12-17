package com.example.libms.services;

import com.example.libms.model.Book;
import com.example.libms.services.BookService;
import com.example.libms.util.DB;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BookServiceTest {
    private Connection connectionMock;
    private PreparedStatement preparedStatementMock;
    private ResultSet resultSetMock;


    @BeforeEach
     void setUp() {
        connectionMock = mock(Connection.class);
        preparedStatementMock = mock(PreparedStatement.class);
       resultSetMock = mock(ResultSet.class);

    }

    @Test
    public void testGetAllBooks() throws SQLException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);


            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);

            when(resultSetMock.next()).thenReturn(true).thenReturn(false);
            when(resultSetMock.getInt("book_id")).thenReturn(1);
            when(resultSetMock.getString("title")).thenReturn("The Great Gatsby");
            when(resultSetMock.getString("genre")).thenReturn("Fiction");
            when(resultSetMock.getString("isbn")).thenReturn("9780743273565");
            when(resultSetMock.getString("is_available")).thenReturn("Yes");
            when(resultSetMock.getInt("copies")).thenReturn(3);


            ObservableList<Book> books = BookService.getAllBooks();


            assertNotNull(books, "The book list should not be null");
            assertEquals(1, books.size(), "There should be one book in the list");

            Book book = books.get(0);
            assertEquals(1, book.getBookId(), "Book ID should be 1");
            assertEquals("The Great Gatsby", book.getTitle(), "Title should match");
            assertEquals("Fiction", book.getGenre(), "Genre should match");
            assertEquals("9780743273565", book.getIsbn(), "ISBN should match");
            assertEquals("Yes", book.getIsAvailable(), "Availability should match");
            assertEquals(3, book.getCopies(), "Number of copies should match");

        }
    }

    @Test
    public void testAddBook() throws SQLException, ClassNotFoundException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(1);

            BookService bookService = new BookService();

            boolean result = bookService.addBook("The Great Gatsby", "Fiction", "9780743273565", "Yes", 3);

            assertTrue(result, "Book should be added successfully");

        }
    }


    @Test
    public void testAddBookFailure() throws SQLException, ClassNotFoundException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(0);

            BookService bookService = new BookService();

            boolean result = bookService.addBook("The Great Gatsby", "Fiction", "9780743273565", "Yes", 3);

            assertFalse(result, "Book should not be added successfully");
        }
    }

    @Test
    public void testAddBookFailure_Exception() throws SQLException, ClassNotFoundException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenThrow(SQLException.class);

            BookService bookService = new BookService();

            boolean result = bookService.addBook("The Great Gatsby", "Fiction", "9780743273565", "Yes", 3);

            assertFalse(result, "Book should not be added successfully");
        }
    }

    @Test
    public void testUpdateBook() throws SQLException, ClassNotFoundException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(1);
            BookService bookService = new BookService();

            boolean result = BookService.updateBook("The Great Gatsby", "Fiction", "9780743273565", "Yes", 5);

            assertTrue(result, "Book should be updated successfully");


            verify(preparedStatementMock, times(1)).setString(1, "The Great Gatsby");
            verify(preparedStatementMock, times(1)).setString(2, "Fiction");
            verify(preparedStatementMock, times(1)).setString(3, "Yes");
            verify(preparedStatementMock, times(1)).setInt(4, 5);
            verify(preparedStatementMock, times(1)).setString(5, "9780743273565");
            verify(preparedStatementMock, times(1)).executeUpdate();
        }
    }

    @Test
    public void testUpdateBookFailure() throws SQLException, ClassNotFoundException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(0); // Simulate failure (no rows affected)

            BookService bookService = new BookService();

            boolean result = BookService.updateBook("The Great Gatsby", "Fiction", "9780743273565", "Yes", 5);

            assertFalse(result, "Book should not be updated successfully");

            verify(preparedStatementMock, times(1)).setString(1, "The Great Gatsby");
            verify(preparedStatementMock, times(1)).setString(2, "Fiction");
            verify(preparedStatementMock, times(1)).setString(3, "Yes");
            verify(preparedStatementMock, times(1)).setInt(4, 5);
            verify(preparedStatementMock, times(1)).setString(5, "9780743273565");
            verify(preparedStatementMock, times(1)).executeUpdate();
        }
    }

    @Test
    public void testUpdateBookFailure_Exception() throws SQLException, ClassNotFoundException {

        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenThrow(SQLException.class);

            BookService bookService = new BookService();

            boolean result = BookService.updateBook("The Great Gatsby", "Fiction", "9780743273565", "Yes", 5);

            assertFalse(result, "Book should not be updated successfully");

            verify(preparedStatementMock, times(1)).setString(1, "The Great Gatsby");
            verify(preparedStatementMock, times(1)).setString(2, "Fiction");
            verify(preparedStatementMock, times(1)).setString(3, "Yes");
            verify(preparedStatementMock, times(1)).setInt(4, 5);
            verify(preparedStatementMock, times(1)).setString(5, "9780743273565");
            verify(preparedStatementMock, times(1)).executeUpdate();
        }
    }

    @Test
    public void testDeleteBook() throws SQLException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(1);

            BookService bookService = new BookService();

            boolean result = bookService.deleteBook("9780743273565");

            assertTrue(result, "Book should be deleted successfully");

            verify(preparedStatementMock, times(1)).setString(1, "9780743273565");
            verify(preparedStatementMock, times(1)).executeUpdate();
        }
    }

    @Test
    public void testDeleteBookFailure() throws SQLException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(0); // Simulate failure (no rows affected)

            BookService bookService = new BookService();

            boolean result = BookService.deleteBook("9780743273565");

            assertFalse(result, "Book should not be deleted");

            verify(preparedStatementMock, times(1)).setString(1, "9780743273565");
            verify(preparedStatementMock, times(1)).executeUpdate();
        }
    }

    @Test
    public void testDeleteBookFailure_Exception() throws SQLException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenThrow(SQLException.class);

            BookService bookService = new BookService();

            boolean result = BookService.deleteBook("9780743273565");

            assertFalse(result, "Book should not be deleted");

            verify(preparedStatementMock, times(1)).setString(1, "9780743273565");
            verify(preparedStatementMock, times(1)).executeUpdate();
        }
    }


    @Test
    public void testSearchBookByIsbn() throws SQLException {


        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true);
            when(resultSetMock.getInt("book_id")).thenReturn(1);
            when(resultSetMock.getString("title")).thenReturn("The Great Gatsby");
            when(resultSetMock.getString("genre")).thenReturn("Fiction");
            when(resultSetMock.getString("isbn")).thenReturn("9780743273565");
            when(resultSetMock.getString("is_available")).thenReturn("Yes");
            when(resultSetMock.getInt("copies")).thenReturn(3);

            BookService bookService = new BookService();
            Book book = BookService.searchBookByIsbn("9780743273565");

            assertNotNull(book);
            assertEquals("The Great Gatsby", book.getTitle());
            assertEquals("9780743273565", book.getIsbn());

            verify(preparedStatementMock, times(1)).setString(1, "9780743273565");
            verify(preparedStatementMock, times(1)).executeQuery();
        }
    }
}


