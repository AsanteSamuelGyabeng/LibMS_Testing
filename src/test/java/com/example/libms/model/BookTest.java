package com.example.libms.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        // Initialize a Book object before each test
        book = new Book(1, "The Great Gatsby", "Fiction", "978-0743273565", "Yes", 3);
    }

    @Test
    void testConstructorWithAllFields() {
        // Verify the constructor with all fields initializes correctly
        assertEquals(1, book.getBookId());
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("Fiction", book.getGenre());
        assertEquals("978-0743273565", book.getIsbn());
        assertEquals("Yes", book.getIsAvailable());
        assertEquals(3, book.getCopies());
    }

    @Test
    void testConstructorWithoutBookId() {
        // Test the constructor without bookId
        Book bookWithoutId = new Book("1984", "Dystopian", "978-0451524935", "No", 5);
        assertEquals("1984", bookWithoutId.getTitle());
        assertEquals("Dystopian", bookWithoutId.getGenre());
        assertEquals("978-0451524935", bookWithoutId.getIsbn());
        assertEquals("No", bookWithoutId.getIsAvailable());
        assertEquals(5, bookWithoutId.getCopies());
    }

    @Test
    void testSetAndGetBookId() {
        book.setBookId(2);
        assertEquals(2, book.getBookId());
    }

    @Test
    void testSetAndGetTitle() {
        book.setTitle("To Kill a Mockingbird");
        assertEquals("To Kill a Mockingbird", book.getTitle());
    }

    @Test
    void testSetAndGetGenre() {
        book.setGenre("Classic");
        assertEquals("Classic", book.getGenre());
    }

    @Test
    void testSetAndGetIsbn() {
        book.setIsbn("978-0061120084");
        assertEquals("978-0061120084", book.getIsbn());
    }

    @Test
    void testSetAndGetIsAvailable() {
        book.setIsAvailable("No");
        assertEquals("No", book.getIsAvailable());
    }

    @Test
    void testSetAndGetCopies() {
        book.setCopies(10);
        assertEquals(10, book.getCopies());
    }

    @Test
    void testProperties() {
        // Test JavaFX properties
        assertEquals(1, book.bookIdProperty().get());
        assertEquals("The Great Gatsby", book.titleProperty().get());
        assertEquals("Fiction", book.genreProperty().get());
        assertEquals("978-0743273565", book.isbnProperty().get());
        assertEquals("Yes", book.isAvailableProperty().get());
        assertEquals(3, book.copiesProperty().get());
    }
}