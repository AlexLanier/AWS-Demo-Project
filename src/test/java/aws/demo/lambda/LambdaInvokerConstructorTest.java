package aws.demo.lambda;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to cover LambdaInvoker constructor
 */
class LambdaInvokerConstructorTest {

    @Test
    void testConstructor() {
        // Test that constructor can be called
        assertDoesNotThrow(() -> {
            LambdaInvoker invoker = new LambdaInvoker();
            assertNotNull(invoker);
        });
    }
}
