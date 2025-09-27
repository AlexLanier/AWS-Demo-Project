package aws.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * Global exception handler to provide safe error responses without exposing stack traces
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logger.error("Unhandled exception occurred", ex);
        
        // Return safe error response without stack trace
        Map<String, String> errorResponse = Map.of(
            "error", "Internal Server Error",
            "message", "An unexpected error occurred. Please try again later.",
            "timestamp", java.time.Instant.now().toString()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("Invalid argument provided: {}", ex.getMessage());
        
        Map<String, String> errorResponse = Map.of(
            "error", "Bad Request",
            "message", "Invalid request parameters",
            "timestamp", java.time.Instant.now().toString()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, String>> handleSecurityException(SecurityException ex) {
        logger.warn("Security violation: {}", ex.getMessage());
        
        Map<String, String> errorResponse = Map.of(
            "error", "Forbidden",
            "message", "Access denied",
            "timestamp", java.time.Instant.now().toString()
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }
}
