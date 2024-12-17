package com.example.libms.services;

import com.example.libms.util.DB;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LendBookManager {


    public void lendBook(int bookId, String studentName, int staffId, Date lendDate, Date returnDate) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.getConnection();

            String sql = "INSERT INTO lend_books (book_id, student_name, staff_id, lend_date, return_date) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);                // Book ID
            ps.setString(2, studentName);        // Student Name
            ps.setInt(3, staffId);               // Staff ID
            ps.setDate(4, lendDate);             // Loan Date
            ps.setDate(5, returnDate);           // Return Date

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                showSuccessAlert();
            } else {
                showErrorAlert();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new SQLException("Error while lending the book: " + e.getMessage());
        } finally {
            ps.close();
            conn.close();
        }
    }

    /**
     * @showSuccessAlert for success
     */
    public void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book lending recorded successfully!", ButtonType.OK);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * @showErrorAlert for error
     */
    public void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Book lending failed " + ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText("Book Lending Failed");
        alert.showAndWait();
    }

}
