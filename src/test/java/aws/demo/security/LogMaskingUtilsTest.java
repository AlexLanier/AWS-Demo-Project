package aws.demo.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogMaskingUtilsTest {

    @Test
    void maskSensitiveData_withNullMessage_returnsNull() {
        // Act
        String result = LogMaskingUtils.maskSensitiveData(null);

        // Assert
        assertNull(result);
    }

    @Test
    void maskSensitiveData_withNoSensitiveData_returnsOriginal() {
        // Arrange
        String message = "This is a normal log message";

        // Act
        String result = LogMaskingUtils.maskSensitiveData(message);

        // Assert
        assertEquals(message, result);
    }

    @Test
    void maskSensitiveData_withAwsAccessKey_masksKey() {
        // Arrange
        String message = "Using access key AKIAIOSFODNN7EXAMPLE";

        // Act
        String result = LogMaskingUtils.maskSensitiveData(message);

        // Assert
        assertTrue(result.contains("AKIA****************"));
        assertFalse(result.contains("AKIAIOSFODNN7EXAMPLE"));
    }

    @Test
    void maskSensitiveData_withAwsSecretKey_masksSecret() {
        // Arrange
        String message = "Secret key: wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEYwJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";

        // Act
        String result = LogMaskingUtils.maskSensitiveData(message);

        // Assert
        assertTrue(result.contains("wJalrXUtnFEMI/K7MDEN****G/bPxRfiCYEXAMPLEKEYwJalrXUtnFEMI/K7MDEN****G/bPxRfiCYEXAMPLEKEY"));
    }

    @Test
    void maskSensitiveData_withPassword_masksPassword() {
        // Arrange
        String message = "password=secret123";

        // Act
        String result = LogMaskingUtils.maskSensitiveData(message);

        // Assert
        assertTrue(result.contains("password=***MASKED***"));
    }

    @Test
    void maskSensitiveData_withToken_masksToken() {
        // Arrange
        String message = "token: abc123xyz";

        // Act
        String result = LogMaskingUtils.maskSensitiveData(message);

        // Assert
        assertTrue(result.contains("token=***MASKED***"));
    }

    @Test
    void maskSensitiveData_withMultipleSensitiveData_masksAll() {
        // Arrange
        String message = "Access key: AKIAIOSFODNN7EXAMPLE, password=secret123, token: abc123";

        // Act
        String result = LogMaskingUtils.maskSensitiveData(message);

        // Assert
        assertTrue(result.contains("AKIA****************"));
        assertTrue(result.contains("password=***MASKED***"));
        assertTrue(result.contains("token=***MASKED***"));
    }

    @Test
    void maskValue_withNull_returnsMasked() {
        // Act
        String result = LogMaskingUtils.maskValue(null);

        // Assert
        assertEquals("***", result);
    }

    @Test
    void maskValue_withShortValue_returnsMasked() {
        // Act
        String result = LogMaskingUtils.maskValue("abc");

        // Assert
        assertEquals("***", result);
    }

    @Test
    void maskValue_withVeryShortValue_returnsMasked() {
        // Act
        String result = LogMaskingUtils.maskValue("ab");

        // Assert
        assertEquals("***", result);
    }

    @Test
    void maskValue_withMediumValue_masksMiddle() {
        // Act
        String result = LogMaskingUtils.maskValue("abcdef");

        // Assert
        assertEquals("ab***ef", result);
    }

    @Test
    void maskValue_withLongValue_masksMiddle() {
        // Act
        String result = LogMaskingUtils.maskValue("abcdefghijklmnop");

        // Assert
        assertEquals("abcd***mnop", result);
    }
}
