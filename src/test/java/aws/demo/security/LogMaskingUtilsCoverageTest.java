package aws.demo.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogMaskingUtilsCoverageTest {

    @Test
    void maskSensitiveData_allPaths_providesCoverage() {
        // Test null input
        assertNull(LogMaskingUtils.maskSensitiveData(null));
        
        // Test empty string
        assertEquals("", LogMaskingUtils.maskSensitiveData(""));
        
        // Test normal message
        String normalMessage = "This is a normal log message";
        assertEquals(normalMessage, LogMaskingUtils.maskSensitiveData(normalMessage));
        
        // Test AWS access key masking
        String messageWithAccessKey = "Using access key AKIAIOSFODNN7EXAMPLE";
        String masked = LogMaskingUtils.maskSensitiveData(messageWithAccessKey);
        assertTrue(masked.contains("AKIA****************"));
        assertFalse(masked.contains("AKIAIOSFODNN7EXAMPLE"));
        
        // Test password masking
        String messageWithPassword = "password=secret123";
        String maskedPassword = LogMaskingUtils.maskSensitiveData(messageWithPassword);
        assertTrue(maskedPassword.contains("password=***MASKED***"));
        
        // Test token masking
        String messageWithToken = "token: abc123";
        String maskedToken = LogMaskingUtils.maskSensitiveData(messageWithToken);
        assertTrue(maskedToken.contains("token=***MASKED***"));
        
        // Test multiple sensitive data
        String messageWithMultiple = "Access key: AKIAIOSFODNN7EXAMPLE, password=secret123";
        String maskedMultiple = LogMaskingUtils.maskSensitiveData(messageWithMultiple);
        assertTrue(maskedMultiple.contains("AKIA****************"));
        assertTrue(maskedMultiple.contains("password=***MASKED***"));
    }

    @Test
    void maskValue_allPaths_providesCoverage() {
        // Test null input
        assertEquals("***", LogMaskingUtils.maskValue(null));
        
        // Test empty string
        assertEquals("***", LogMaskingUtils.maskValue(""));
        
        // Test short values
        assertEquals("***", LogMaskingUtils.maskValue("a"));
        assertEquals("***", LogMaskingUtils.maskValue("ab"));
        assertEquals("***", LogMaskingUtils.maskValue("abc"));
        assertEquals("***", LogMaskingUtils.maskValue("abcd"));
        
        // Test medium values (5-8 characters)
        assertEquals("ab***ef", LogMaskingUtils.maskValue("abcdef"));
        assertEquals("ab***gh", LogMaskingUtils.maskValue("abcdefgh"));
        
        // Test long values (9+ characters)
        assertEquals("abcd***mnop", LogMaskingUtils.maskValue("abcdefghijklmnop"));
        assertEquals("abcd***wxyz", LogMaskingUtils.maskValue("abcdefghijklmnopqrstuvwxyz"));
    }
}
