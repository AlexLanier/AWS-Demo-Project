package aws.demo.step;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to cover StepFunctionRunner constructor
 */
class StepFunctionRunnerConstructorTest {

    @Test
    void testConstructor() {
        // Test that constructor can be called
        assertDoesNotThrow(() -> {
            StepFunctionRunner runner = new StepFunctionRunner();
            assertNotNull(runner);
        });
    }
}
