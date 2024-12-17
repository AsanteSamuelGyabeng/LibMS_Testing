package com.example.libms.services;

import com.example.libms.services.LendBookManager;
import com.example.libms.util.DB;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LendBookManagerTest {

    @Test
    public void testLendBookSuccess() throws SQLException {
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(1); // Simulate success

            LendBookManager lendBookManager = new LendBookManager();

            LendBookManager spyLendBookManager = spy(lendBookManager);
            doNothing().when(spyLendBookManager).showSuccessAlert();

            spyLendBookManager.lendBook(1, "John Doe", 101, Date.valueOf("2024-12-01"), Date.valueOf("2024-12-15"));


            verify(preparedStatementMock, times(1)).setInt(1, 1);
            verify(preparedStatementMock, times(1)).setString(2, "John Doe");
            verify(preparedStatementMock, times(1)).setInt(3, 101);
            verify(preparedStatementMock, times(1)).setDate(4, Date.valueOf("2024-12-01"));
            verify(preparedStatementMock, times(1)).setDate(5, Date.valueOf("2024-12-15"));
            verify(preparedStatementMock, times(1)).executeUpdate();

            // Verifying that showSuccessAlert was called
            verify(spyLendBookManager, times(1)).showSuccessAlert();
        }
    }

    @Test
    public void testLendBookFailure() throws SQLException {
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(0); // Simulate failure

            LendBookManager lendBookManager = new LendBookManager();

            // Mock showErrorAlert to avoid UI popups
            LendBookManager spyLendBookManager = spy(lendBookManager);
            doNothing().when(spyLendBookManager).showErrorAlert();

            spyLendBookManager.lendBook(1, "John Doe", 101, Date.valueOf("2024-12-01"), Date.valueOf("2024-12-15"));

            // Verifying the interaction with database

            verify(preparedStatementMock, times(1)).setInt(1, 1);
            verify(preparedStatementMock, times(1)).setString(2, "John Doe");
            verify(preparedStatementMock, times(1)).setInt(3, 101);
            verify(preparedStatementMock, times(1)).setDate(4, Date.valueOf("2024-12-01"));
            verify(preparedStatementMock, times(1)).setDate(5, Date.valueOf("2024-12-15"));
            verify(preparedStatementMock, times(1)).executeUpdate();

            // Verifying that showErrorAlert was called
            verify(spyLendBookManager, times(1)).showErrorAlert();
        }
    }

    @Test
    public void testLendBookSQLException() throws SQLException {
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenThrow(new SQLException("Database error"));

            LendBookManager lendBookManager = new LendBookManager();

            SQLException exception = assertThrows(SQLException.class, () -> {
                lendBookManager.lendBook(1, "John Doe", 101, Date.valueOf("2024-12-01"), Date.valueOf("2024-12-15"));
            });

            assertEquals("Error while lending the book: Database error", exception.getMessage());

            // Verifying interactions
            verify(preparedStatementMock, times(1)).setInt(1, 1);
            verify(preparedStatementMock, times(1)).setString(2, "John Doe");
            verify(preparedStatementMock, times(1)).setInt(3, 101);
            verify(preparedStatementMock, times(1)).setDate(4, Date.valueOf("2024-12-01"));
            verify(preparedStatementMock, times(1)).setDate(5, Date.valueOf("2024-12-15"));
        }
    }
}
