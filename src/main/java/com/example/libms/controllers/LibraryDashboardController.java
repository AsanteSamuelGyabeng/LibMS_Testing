package com.example.libms.controllers;

import com.example.libms.SceneController;
import com.example.libms.services.LendBookManager;
import com.example.libms.util.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class LibraryDashboardController {

    @FXML
    private StackPane mainContent;
    @FXML
    private Button btnBooks;
    @FXML
    private Button btnMembers;
    @FXML
    private Button btnLoans;
    @FXML
    private Button btnReports;

    @FXML
    private Label welcomeLabel;

    private String staffMember;

    /**
     * Sets the username of the staff member
     */
    public void setUsername(String username) throws SQLException {
        welcomeLabel.setText("Welcome, " + username + "!");
        staffMember = username;

        Connection conn = DB.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM staff WHERE username = ?");
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int staffId = rs.getInt("id");
            String staffName = rs.getString("username");
            System.out.println("Staff ID: " + staffId);
            System.out.println("Staff ID: " + staffId);
        }

        rs.close();
        ps.close();
        conn.close();
    }

    /**
     * Initializes the dashboard and handles button actions
     */
    @FXML
    public void initialize() {
        // Set button actions
        btnBooks.setOnAction(e -> {
            try {
                openBooksPage(e);
            } catch (IOException ex) {
                Logger logger = Logger.getLogger(LibraryDashboardController.class.getName());
                logger.severe("Error opening books page: " + ex.getMessage());
            }
        });
        btnMembers.setOnAction(e -> {
            try {
                switchContent(createMembersPane());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnLoans.setOnAction(e -> switchContent(createLoansPane()));
        btnReports.setOnAction(e -> switchContent(createReportsPane()));
    }

    /**
     * Opens the books page
     */
    private void openBooksPage(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchScene("books_view.fxml", event);
    }

    /**
     * Replaces the current content in the mainContent pane
     */
    private void switchContent(Pane newContent) {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(newContent);
    }

    /**
     * Creates a pane for member registration
     */
    private Pane createMembersPane() throws SQLException {
        VBox pane = new VBox(15);
        pane.setPadding(new Insets(20));
        Label membersPanelLabel = new Label("Staff Registration");
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter Staff Name");
        TextField emailInput = new TextField();
        emailInput.setPromptText("Enter Email");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter Password");
        Button submitButton = new Button("Register Staff");

        StaffController staffController = new StaffController();
        submitButton.setOnAction(e -> staffController.handleSignUpAction(nameInput.getText(), emailInput.getText(), passwordInput.getText()));

        pane.getChildren().addAll(membersPanelLabel, nameInput, emailInput, passwordInput, submitButton);
        return pane;
    }

    /**
     * Creates a pane for lending books
     */
    private Pane createLoansPane() {
        VBox pane = new VBox(15);
        pane.setPadding(new Insets(20));


        Label title = new Label("Lend a Book");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Input fields for book ID, student name, staff ID, loan date, and return date
        TextField bookIdField = new TextField();
        bookIdField.setPromptText("Enter Book ID");

        TextField studentNameField = new TextField();
        studentNameField.setPromptText("Enter Student Name");

        TextField staffIdField = new TextField();
        staffIdField.setPromptText("Enter Staff ID");

        TextField loanDateField = new TextField();
        loanDateField.setPromptText("Enter Loan Date (YYYY-MM-DD)");

        TextField returnDateField = new TextField();
        returnDateField.setPromptText("Enter Return Date (YYYY-MM-DD)");

        // Button to trigger the lending action
        Button lendBookButton = new Button("Lend Book");

        lendBookButton.setOnAction(e -> {
            try {
                // Get the values from the input fields
                String bookIdStr = bookIdField.getText();
                String studentName = studentNameField.getText();
                String staffIdStr = staffIdField.getText();
                String loanDateStr = loanDateField.getText();
                String returnDateStr = returnDateField.getText();


                int bookId = Integer.parseInt(bookIdStr);
                int staffId = Integer.parseInt(staffIdStr);
                Date loanDate = Date.valueOf(loanDateStr);
                Date returnDate = Date.valueOf(returnDateStr);

                LendBookManager manager = new LendBookManager();
                manager.lendBook(bookId, studentName, staffId, loanDate, returnDate);
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                System.out.println("Error lending book. Please check the inputs and try again.");
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                System.out.println("Invalid date format. Please enter a valid date.");
            }
        });

        pane.getChildren().addAll(title, bookIdField, studentNameField, staffIdField, loanDateField, returnDateField, lendBookButton);

        return pane;
    }


    /**
     * Handles the lending of a book
     */
    private void handleLendBookAction(String bookId, String memberId, String loanDate) throws SQLException {
        if (bookId.isEmpty() || memberId.isEmpty() || loanDate.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }

        String query = "INSERT INTO lend_books (book_id, staff_id, lend_date) VALUES (?, ?, ?)";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, Integer.parseInt(bookId));
            statement.setInt(2, Integer.parseInt(memberId));
            statement.setDate(3, java.sql.Date.valueOf(loanDate));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book lent successfully!");
            } else {
                System.out.println("Failed to lend the book.");
            }
        }
    }

    /**
     * Creates a pane for reports
     */
    private Pane createReportsPane() {
        VBox pane = new VBox(10);
        pane.getChildren().add(new Label("Reports Panel"));
        return pane;
    }


}
