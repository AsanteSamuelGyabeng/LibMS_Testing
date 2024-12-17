package com.example.libms.services;

import com.example.libms.services.LoginService;
import com.example.libms.util.DB;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {

    @ParameterizedTest
    @CsvSource({
            "'admin', 'password123', true",  // Test case 1: Success
            "'admin', 'wrongpassword', false",  // Test case 2: Failure
            "'admin', 'password123', false",   // Test case 3: Database error simulation
            "'', 'password123', false",        // Test case 4: Empty username
            "'admin', '', false",              // Test case 5: Empty password
            "'admin', 'nullpassword', false"   // Test case 6: Simulate failed login with null password
    })
    public void testAuthenticateUser(String username, String password, boolean expectedResult) throws SQLException {
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        try (MockedStatic<DB> dbMock = Mockito.mockStatic(DB.class)) {
            dbMock.when(DB::getConnection).thenReturn(connectionMock);

            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);

            if (expectedResult) {
                when(resultSetMock.next()).thenReturn(true); // Simulate successful login
            } else {
                when(resultSetMock.next()).thenReturn(false); // Simulate failed login or database error
                when(preparedStatementMock.executeQuery()).thenThrow(new SQLException("Database error")); // Simulate database error
            }

            LoginService loginService = new LoginService();
            boolean result = loginService.authenticateUser(username, password);

            assertEquals(expectedResult, result);

            verify(preparedStatementMock, times(1)).setString(1, username);
            verify(preparedStatementMock, times(1)).setString(2, password);
            verify(preparedStatementMock, times(1)).executeQuery();
        }
    }
}
