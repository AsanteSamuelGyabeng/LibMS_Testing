package com.example.libms.model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import com.example.libms.util.DB;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {

    private IntegerProperty bookId;
    private StringProperty title;
    private StringProperty genre;
    private StringProperty isbn;
    private StringProperty isAvailable;
    private IntegerProperty copies;

    public Book(int bookId, String title, String genre, String isbn, String isAvailable, int copies) {
        this.bookId = new SimpleIntegerProperty(bookId);
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.isbn = new SimpleStringProperty(isbn);
        this.isAvailable = new SimpleStringProperty(isAvailable);
        this.copies = new SimpleIntegerProperty(copies);
    }

    public Book(String title, String genre, String isbn, String isAvailable, int copies) {
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.isbn = new SimpleStringProperty(isbn);
        this.isAvailable = new SimpleStringProperty(isAvailable);
        this.copies = new SimpleIntegerProperty(copies);
    }


    public IntegerProperty bookIdProperty() {
        return bookId; // Returns IntegerProperty
    }

    public int getBookId() {
        return bookId.get(); // Returns the int value of bookId
    }

    public void setBookId(int bookId) {
        this.bookId.set(bookId); // Sets the int value of bookId
    }

    public StringProperty titleProperty() {
        return title; // Returns StringProperty
    }

    public String getTitle() {
        return title.get(); // Returns the value of title
    }

    public void setTitle(String title) {
        this.title.set(title); // Sets the value of title
    }

    public StringProperty genreProperty() {
        return genre; // Returns StringProperty
    }

    public String getGenre() {
        return genre.get(); // Returns the value of genre
    }

    public void setGenre(String genre) {
        this.genre.set(genre); // Sets the value of genre
    }

    public StringProperty isbnProperty() {
        return isbn; // Returns StringProperty
    }

    public String getIsbn() {
        return isbn.get(); // Returns the value of isbn
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn); // Sets the value of isbn
    }

    public StringProperty isAvailableProperty() {
        return isAvailable; // Returns StringProperty
    }

    public String getIsAvailable() {
        return isAvailable.get(); // Returns the value of isAvailable
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable.set(isAvailable); // Sets the value of isAvailable
    }

    public IntegerProperty copiesProperty() {
        return copies; // Returns IntegerProperty
    }

    public int getCopies() {
        return copies.get(); // Returns the value of copies
    }

    public void setCopies(int copies) {
        this.copies.set(copies); // Sets the value of copies
    }





}
