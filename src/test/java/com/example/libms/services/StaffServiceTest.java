package com.example.libms.services;

import com.example.libms.services.StaffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StaffServiceTest {

    private StaffService staffService;
    private PreparedStatement preparedStatement;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        staffService = new StaffService(connection);
    }

    @ParameterizedTest
    @CsvSource({
            "'testUser', 'test@domain.com', 'testPassword', 1, true",  // Successful registration
            "'testUser', 'test@domain.com', 'testPassword', 0, false"   // Unsuccessful registration
    })
    public void testRegisterStaff(String username, String email, String password, int executeUpdateReturnValue, boolean expectedResult) throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(executeUpdateReturnValue);

        boolean isRegistered = staffService.registerStaff(username, email, password);

        assertEquals(expectedResult, isRegistered);

        verify(preparedStatement, times(1)).executeUpdate();
    }
}
