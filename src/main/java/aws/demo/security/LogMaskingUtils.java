package aws.demo.security;

import java.util.regex.Pattern;

/**
 * Utility class for masking sensitive values in logs
 */
public class LogMaskingUtils {

    // Pattern to match AWS access keys
    private static final Pattern AWS_ACCESS_KEY_PATTERN = Pattern.compile("AKIA[0-9A-Z]{16}");
    
    // Pattern to match AWS secret keys (partial)
    private static final Pattern AWS_SECRET_KEY_PATTERN = Pattern.compile("([A-Za-z0-9/+=]{20})([A-Za-z0-9/+=]{20})");
    
    // Pattern to match passwords in various formats
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?i)(password|pass|pwd|token)\\s*[:=]\\s*([^\\s,}]+)");

    /**
     * Masks sensitive information in log messages
     * @param message the log message to mask
     * @return the masked message
     */
    public static String maskSensitiveData(String message) {
        if (message == null) {
            return null;
        }

        String masked = message;

        // Mask AWS access keys
        masked = AWS_ACCESS_KEY_PATTERN.matcher(masked).replaceAll("AKIA****************");

        // Mask AWS secret keys (show first 4 and last 4 characters)
        masked = AWS_SECRET_KEY_PATTERN.matcher(masked).replaceAll("$1****$2");

        // Mask passwords and tokens
        masked = PASSWORD_PATTERN.matcher(masked).replaceAll("$1=***MASKED***");

        return masked;
    }

    /**
     * Masks a specific value
     * @param value the value to mask
     * @return the masked value
     */
    public static String maskValue(String value) {
        if (value == null || value.length() <= 4) {
            return "***";
        }
        
        if (value.length() <= 8) {
            return value.substring(0, 2) + "***" + value.substring(value.length() - 2);
        }
        
        return value.substring(0, 4) + "***" + value.substring(value.length() - 4);
    }
}
