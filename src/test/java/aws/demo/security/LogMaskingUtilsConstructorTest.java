package aws.demo.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to cover LogMaskingUtils constructor
 */
class LogMaskingUtilsConstructorTest {

    @Test
    void testConstructor() {
        // Test that constructor can be called
        assertDoesNotThrow(() -> {
            LogMaskingUtils utils = new LogMaskingUtils();
            assertNotNull(utils);
        });
    }
}
