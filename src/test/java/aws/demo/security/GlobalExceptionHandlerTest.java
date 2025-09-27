package aws.demo.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleGenericException_returnsInternalServerError() {
        // Arrange
        Exception exception = new RuntimeException("Test exception");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleGenericException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody().get("message"));
    }

    @Test
    void handleIllegalArgumentException_returnsBadRequest() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Invalid input");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleIllegalArgumentException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Invalid request parameters", response.getBody().get("message"));
    }

    @Test
    void handleSecurityException_returnsForbidden() {
        // Arrange
        SecurityException exception = new SecurityException("Access denied");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleSecurityException(exception);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Forbidden", response.getBody().get("error"));
        assertEquals("Access denied", response.getBody().get("message"));
    }

    @Test
    void handleGenericException_withNullMessage_returnsInternalServerError() {
        // Arrange
        Exception exception = new RuntimeException();

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleGenericException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody().get("message"));
    }

    @Test
    void handleIllegalArgumentException_withNullMessage_returnsBadRequest() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException();

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleIllegalArgumentException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Invalid request parameters", response.getBody().get("message"));
    }

    @Test
    void handleSecurityException_withNullMessage_returnsForbidden() {
        // Arrange
        SecurityException exception = new SecurityException();

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleSecurityException(exception);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Forbidden", response.getBody().get("error"));
        assertEquals("Access denied", response.getBody().get("message"));
    }
}