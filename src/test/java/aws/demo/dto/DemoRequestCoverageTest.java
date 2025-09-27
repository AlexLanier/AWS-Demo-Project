package aws.demo.dto;

import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DemoRequestCoverageTest {

    @Test
    void demoRequest_allMethods_provideCoverage() {
        // Test all methods to provide maximum coverage
        DemoRequest request = new DemoRequest();
        
        // Test setters and getters
        request.setSource("test-source");
        request.setValue(123);
        request.setMessage("test message");
        
        assertEquals("test-source", request.getSource());
        assertEquals(123, request.getValue());
        assertEquals("test message", request.getMessage());
        
        // Test toString
        String toString = request.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("DemoRequest"));
        
        // Test parameterized constructor
        DemoRequest request2 = new DemoRequest("source2", 456, "message2");
        assertEquals("source2", request2.getSource());
        assertEquals(456, request2.getValue());
        assertEquals("message2", request2.getMessage());
    }

    @Test
    void demoRequest_validation_providesCoverage() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        // Test valid request
        DemoRequest validRequest = new DemoRequest("valid-source", 123, "valid message");
        Set<ConstraintViolation<DemoRequest>> violations = validator.validate(validRequest);
        assertTrue(violations.isEmpty());
        
        // Test invalid requests to cover validation paths
        DemoRequest invalidRequest = new DemoRequest();
        violations = validator.validate(invalidRequest);
        assertFalse(violations.isEmpty());
        
        // Test boundary values
        DemoRequest boundaryRequest = new DemoRequest();
        boundaryRequest.setSource("a".repeat(100)); // Exactly 100 characters
        boundaryRequest.setValue(123);
        boundaryRequest.setMessage("a".repeat(500)); // Exactly 500 characters
        violations = validator.validate(boundaryRequest);
        assertTrue(violations.isEmpty());
        
        // Test over boundary values
        DemoRequest overBoundaryRequest = new DemoRequest();
        overBoundaryRequest.setSource("a".repeat(101)); // Over 100 characters
        overBoundaryRequest.setValue(123);
        overBoundaryRequest.setMessage("a".repeat(501)); // Over 500 characters
        violations = validator.validate(overBoundaryRequest);
        assertFalse(violations.isEmpty());
    }
}
