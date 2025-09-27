package aws.demo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemoRequestTest {

    @Test
    void defaultConstructor_createsEmptyObject() {
        // Act
        DemoRequest request = new DemoRequest();

        // Assert
        assertNull(request.getSource());
        assertNull(request.getValue());
        assertNull(request.getMessage());
    }

    @Test
    void parameterizedConstructor_setsAllFields() {
        // Arrange
        String source = "test-source";
        Integer value = 42;
        String message = "test message";

        // Act
        DemoRequest request = new DemoRequest(source, value, message);

        // Assert
        assertEquals(source, request.getSource());
        assertEquals(value, request.getValue());
        assertEquals(message, request.getMessage());
    }

    @Test
    void settersAndGetters_workCorrectly() {
        // Arrange
        DemoRequest request = new DemoRequest();
        String source = "test-source";
        Integer value = 100;
        String message = "test message";

        // Act
        request.setSource(source);
        request.setValue(value);
        request.setMessage(message);

        // Assert
        assertEquals(source, request.getSource());
        assertEquals(value, request.getValue());
        assertEquals(message, request.getMessage());
    }

    @Test
    void toString_returnsCorrectFormat() {
        // Arrange
        DemoRequest request = new DemoRequest("test-source", 42, "test message");

        // Act
        String result = request.toString();

        // Assert
        assertTrue(result.contains("DemoRequest{"));
        assertTrue(result.contains("source='test-source'"));
        assertTrue(result.contains("value=42"));
        assertTrue(result.contains("message='test message'"));
    }

    @Test
    void toString_withNullValues_handlesCorrectly() {
        // Arrange
        DemoRequest request = new DemoRequest();
        request.setSource(null);
        request.setValue(null);
        request.setMessage(null);

        // Act
        String result = request.toString();

        // Assert
        assertTrue(result.contains("DemoRequest{"));
        assertTrue(result.contains("source='null'"));
        assertTrue(result.contains("value=null"));
        assertTrue(result.contains("message='null'"));
    }

    @Test
    void setSource_withNullValue_works() {
        // Arrange
        DemoRequest request = new DemoRequest();
        request.setSource("initial");

        // Act
        request.setSource(null);

        // Assert
        assertNull(request.getSource());
    }

    @Test
    void setValue_withNullValue_works() {
        // Arrange
        DemoRequest request = new DemoRequest();
        request.setValue(42);

        // Act
        request.setValue(null);

        // Assert
        assertNull(request.getValue());
    }

    @Test
    void setMessage_withNullValue_works() {
        // Arrange
        DemoRequest request = new DemoRequest();
        request.setMessage("initial");

        // Act
        request.setMessage(null);

        // Assert
        assertNull(request.getMessage());
    }

    @Test
    void setValue_withNegativeValue_works() {
        // Arrange
        DemoRequest request = new DemoRequest();

        // Act
        request.setValue(-1);

        // Assert
        assertEquals(-1, request.getValue());
    }

    @Test
    void setValue_withZero_works() {
        // Arrange
        DemoRequest request = new DemoRequest();

        // Act
        request.setValue(0);

        // Assert
        assertEquals(0, request.getValue());
    }

    @Test
    void setSource_withEmptyString_works() {
        // Arrange
        DemoRequest request = new DemoRequest();

        // Act
        request.setSource("");

        // Assert
        assertEquals("", request.getSource());
    }

    @Test
    void setMessage_withEmptyString_works() {
        // Arrange
        DemoRequest request = new DemoRequest();

        // Act
        request.setMessage("");

        // Assert
        assertEquals("", request.getMessage());
    }
}