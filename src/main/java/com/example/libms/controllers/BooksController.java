package com.example.libms.controllers;

import com.example.libms.SceneController;
import com.example.libms.model.Book;
import com.example.libms.services.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class BooksController {

    @FXML
    public TableView<Book> booksTable;

    @FXML
    public TableColumn<Book, Integer> bookIdColumn;

    @FXML
    public TableColumn<Book, String> titleColumn;

    @FXML
    TableColumn<Book, String> genreColumn;

    @FXML
    TableColumn<Book, String> isbnColumn;

    @FXML
    TableColumn<Book, String> isAvailableColumn;

    @FXML
    public TableColumn<Book, Integer> copiesColumn;

    @FXML
    public  TextField titleField;

    @FXML
    public  TextField genreField;

    @FXML
    public  TextField isbnField;
    @FXML
    public TextField searchIsbnField;

    @FXML
    public  TextField isAvailableField;

    @FXML
    public  TextField copiesField;

    @FXML
    public Button addButton;

    @FXML
    public Button updateButton;

    @FXML
    public Button deleteButton;

    @FXML
    public Button clearButton;

    BookService newBook = new BookService();

    // Initialize method
    public void initialize() throws ClassNotFoundException {
        // Initialize the table columns
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        isAvailableColumn.setCellValueFactory(cellData -> cellData.getValue().isAvailableProperty());
        copiesColumn.setCellValueFactory(cellData -> cellData.getValue().copiesProperty().asObject());



        loadBooks();
    }

 public void loadBooks() throws ClassNotFoundException {
        ObservableList<Book> books = BookService.getAllBooks();
        booksTable.setItems(books);
    }

    @FXML
    public void handleAddBook() throws ClassNotFoundException {
        String title = titleField.getText();
        String genre = genreField.getText();
        String isbn = isbnField.getText();
        String isAvailable = isAvailableField.getText();
        int copies = Integer.parseInt(copiesField.getText());

        if (newBook.addBook(title, genre, isbn, isAvailable, copies)) {
            loadBooks();
            clearFields();
            showAlert("Success", "Book added successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to add book.", Alert.AlertType.ERROR);
        }
    }

    // Update an existing book
    @FXML
    private void handleUpdateBook() throws ClassNotFoundException {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Error", "No book selected.", Alert.AlertType.ERROR);
            return;
        }
        selectedBook.setTitle(titleField.getText());
        selectedBook.setGenre(genreField.getText());
        selectedBook.setIsbn(isbnField.getText());
        selectedBook.setIsAvailable(isAvailableField.getText());
        selectedBook.setCopies(Integer.parseInt(copiesField.getText()));

        if (BookService.updateBook(titleField.getText(),genreField.getText(),isbnField.getText(),isAvailableField.getText(), Integer.parseInt(copiesField.getText()))) {
            // Refresh the table after updating the book
            loadBooks();
            clearFields();
            showAlert("Success", "Book updated successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update book.", Alert.AlertType.ERROR);
        }
    }

    // Delete a book
    @FXML
    private void handleDeleteBook() throws ClassNotFoundException {
        if (BookService.deleteBook(searchIsbnField.getText())) {
            loadBooks();
            clearFields();
            showAlert("Success", "Book deleted successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to delete book.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSearchBook() throws ClassNotFoundException {
        String isbn = searchIsbnField.getText(); // Get the ISBN entered by the user

        if (isbn == null || isbn.isEmpty()) {
            showAlert("Error", "Please enter an ISBN to search.", Alert.AlertType.ERROR);
            return;
        }

        Book book = BookService.searchBookByIsbn(isbn);
        if (book != null) {
            booksTable.getSelectionModel().clearSelection();

            ObservableList<Book> searchResults = FXCollections.observableArrayList();
            searchResults.add(book);
            booksTable.setItems(searchResults);
        } else {
            showAlert("Not Found", "No book found with the given ISBN.", Alert.AlertType.INFORMATION);
        }
    }

    // Clear the input fields
    @FXML
    private void handleClearFields() {
        clearFields();
    }

    // Helper method to clear all input fields
    public void clearFields() {
        titleField.clear();
        genreField.clear();
        isbnField.clear();
        isAvailableField.clear();
        copiesField.clear();
    }

    public void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchScene("dashboard.fxml", event);
    }

}
